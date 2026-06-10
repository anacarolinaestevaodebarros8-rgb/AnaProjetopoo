package com.folhear.service;

import com.folhear.entity.Livro;
import com.folhear.entity.Usuario;
import com.folhear.entity.enums.TipoAnuncio;
import com.folhear.repository.LivroRepository;
import com.folhear.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * LivroService demonstra:
 *  1. Operações JPA/Hibernate diretas no banco Supabase (PostgreSQL)
 *  2. Chamadas opcionais via PostgREST (ex.: filtros avançados ou RPC functions)
 */
@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;

    @Qualifier("supabaseClient")
    private final WebClient supabaseClient;

    // ---- Consultas via JPA (direto no banco) --------------------------

    public List<Livro> listarAtivos() {
        return livroRepository.findByTipoAnuncioAndAtivoTrue(TipoAnuncio.AMBOS);
    }

    public List<Livro> listarPorVendedor(UUID vendedorId) {
        return livroRepository.findByVendedorId(vendedorId);
    }

    public List<Livro> buscar(String query) {
        return livroRepository.buscar(query);
    }

    public Livro buscarPorId(UUID id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado: " + id));
    }

    // ---- Mutações via JPA (transacionais) ----------------------------

    @Transactional
    public Livro cadastrar(Livro livro, UUID vendedorId) {
        Usuario vendedor = usuarioRepository.findById(vendedorId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado: " + vendedorId));
        livro.setVendedor(vendedor);
        return livroRepository.save(livro);
    }

    @Transactional
    public Livro editar(UUID id, Livro dados, UUID solicitanteId) {
        Livro livro = buscarPorId(id);
        validarProprietario(livro, solicitanteId);

        livro.setTitulo(dados.getTitulo());
        livro.setAutor(dados.getAutor());
        livro.setIsbn(dados.getIsbn());
        livro.setCategoria(dados.getCategoria());
        livro.setEstado(dados.getEstado());
        livro.setTipoAnuncio(dados.getTipoAnuncio());
        livro.setPreco(dados.getPreco());
        return livroRepository.save(livro);
    }

    @Transactional
    public void remover(UUID id, UUID solicitanteId) {
        Livro livro = buscarPorId(id);
        validarProprietario(livro, solicitanteId);
        livro.setAtivo(false);       // soft delete
        livroRepository.save(livro);
    }

    // ---- Chamada via PostgREST (filtro geoespacial ou RPC) -----------

    /**
     * Exemplo: busca livros próximos usando uma RPC function do Supabase.
     * No Supabase crie: CREATE FUNCTION livros_proximos(lat float, lng float, raio_km float)
     */
    public List<Map> livrosProximos(double lat, double lng, double raioKm) {
        return supabaseClient.post()
                .uri("/rpc/livros_proximos")
                .bodyValue(Map.of("lat", lat, "lng", lng, "raio_km", raioKm))
                .retrieve()
                .bodyToFlux(Map.class)
                .collectList()
                .block();
    }

    // ---- helpers -----------------------------------------------------

    private void validarProprietario(Livro livro, UUID solicitanteId) {
        if (!livro.getVendedor().getId().equals(solicitanteId)) {
            throw new SecurityException("Acesso negado: você não é o dono deste livro.");
        }
    }
}