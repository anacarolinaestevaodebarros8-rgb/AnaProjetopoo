package com.folhear.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

/**
 * Valida o JWT emitido pelo Supabase Auth.
 * O token chega no header: Authorization: Bearer <supabase_jwt>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Endpoints públicos (leitura)
                .requestMatchers(HttpMethod.GET, "/livros/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/obras/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/pontos-encontro/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/clubes/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/avaliacoes/**").permitAll()
                // Tudo mais exige autenticação
                .anyRequest().authenticated()
            )
            .addFilterBefore(supabaseJwtFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public SupabaseJwtFilter supabaseJwtFilter() {
        return new SupabaseJwtFilter(jwtSecret);
    }

    // ------------------------------------------------------------------
    //  Filtro JWT
    // ------------------------------------------------------------------
    public static class SupabaseJwtFilter extends OncePerRequestFilter {

        private final String secret;

        public SupabaseJwtFilter(String secret) {
            this.secret = secret;
        }

        @Override
        protected void doFilterInternal(HttpServletRequest request,
                                        HttpServletResponse response,
                                        FilterChain chain)
                throws ServletException, IOException {

            String header = request.getHeader("Authorization");

            if (header != null && header.startsWith("Bearer ")) {
                String token = header.substring(7);
                try {
                    SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

                    Claims claims = Jwts.parser()
                            .verifyWith(key)
                            .build()
                            .parseSignedClaims(token)
                            .getPayload();

                    String sub  = claims.getSubject();          // UUID do usuário no Supabase
                    String role = claims.get("role", String.class); // "authenticated" ou "anon"

                    if (sub != null) {
                        var auth = new UsernamePasswordAuthenticationToken(
                                UUID.fromString(sub),
                                null,
                                List.of(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()))
                        );
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }

                } catch (Exception e) {
                    // Token inválido → segue sem autenticar (será barrado pelo authorizeHttpRequests)
                    SecurityContextHolder.clearContext();
                }
            }

            chain.doFilter(request, response);
        }
    }
}