package com.folhear.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configura o WebClient apontando para a REST API do Supabase (PostgREST).
 *
 * Uso:
 *   @Autowired WebClient supabaseClient;
 *   supabaseClient.get().uri("/livro?ativo=eq.true").retrieve().bodyToFlux(...)
 */
@Configuration
public class SupabaseClientConfig {

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.key}")
    private String supabaseAnonKey;

    @Value("${supabase.service-key:}")
    private String supabaseServiceKey;

    /** Client público (usa anon key — respeita RLS) */
    @Bean(name = "supabaseClient")
    public WebClient supabaseClient() {
        return WebClient.builder()
                .baseUrl(supabaseUrl + "/rest/v1")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("apikey", supabaseAnonKey)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + supabaseAnonKey)
                .build();
    }

    /** Client de serviço (service role key — ignora RLS, usar só no backend) */
    @Bean(name = "supabaseServiceClient")
    public WebClient supabaseServiceClient() {
        return WebClient.builder()
                .baseUrl(supabaseUrl + "/rest/v1")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("apikey", supabaseServiceKey)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + supabaseServiceKey)
                .build();
    }
}