package com.folhear.repository;

import com.folhear.entity.*;
import com.folhear.entity.enums.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// ---- Usuario -------------------------------------------------------

@Repository
interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByCpf(String cpf);
    List<Usuario> findByCidade(String cidade);
    boolean existsByEmail(String email);
}

// ---- Livro ---------------------------------------------------------

@Repository
interface LivroRepository extends JpaRepository<Livro, UUID> {
    List<Livro> findByVendedorId(UUID vendedorId);
    List<Livro> findByTipoAnuncioAndAtivoTrue(TipoAnuncio tipo);
    List<Livro> findByCategoriaAndAtivoTrue(String categoria);

    @Query("SELECT l FROM Livro l WHERE l.ativo = true AND " +
           "(LOWER(l.titulo) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(l.autor) LIKE LOWER(CONCAT('%', :q, '%')))")
    List<Livro> buscar(@Param("q") String query);
}

// ---- Troca ---------------------------------------------------------

@Repository
interface TrocaRepository extends JpaRepository<Troca, UUID> {
    List<Troca> findByProponenteId(UUID proponenteId);
    List<Troca> findByReceptorId(UUID receptorId);
    List<Troca> findByStatus(StatusTroca status);

    @Query("SELECT t FROM Troca t WHERE t.proponente.id = :uid OR t.receptor.id = :uid")
    List<Troca> findByParticipante(@Param("uid") UUID usuarioId);
}

// ---- Transacao -----------------------------------------------------

@Repository
interface TransacaoRepository extends JpaRepository<Transacao, UUID> {
    List<Transacao> findByCompradorId(UUID compradorId);
    List<Transacao> findByVendedorId(UUID vendedorId);
    List<Transacao> findByStatus(StatusTransacao status);
}

// ---- ObraAutoral ---------------------------------------------------

@Repository
interface ObraAutorialRepository extends JpaRepository<ObraAutoral, UUID> {
    List<ObraAutoral> findByAutorId(UUID autorId);
    List<ObraAutoral> findByDestaqueLocalTrue();

    @Query("SELECT o FROM ObraAutoral o WHERE " +
           "LOWER(o.titulo) LIKE LOWER(CONCAT('%', :q, '%'))")
    List<ObraAutoral> buscar(@Param("q") String query);
}

// ---- Capitulo ------------------------------------------------------

@Repository
interface CapituloRepository extends JpaRepository<Capitulo, UUID> {
    List<Capitulo> findByObraIdOrderByNumeroAsc(UUID obraId);
    List<Capitulo> findByObraIdAndGratuitoTrue(UUID obraId);
}

// ---- PontoEncontro -------------------------------------------------

@Repository
interface PontoEncontroRepository extends JpaRepository<PontoEncontro, UUID> {
    List<PontoEncontro> findByAtivoTrue();
    List<PontoEncontro> findByTipo(com.folhear.entity.enums.TipoPonto tipo);

    @Query("SELECT p FROM PontoEncontro p WHERE p.ativo = true " +
           "ORDER BY (6371 * acos(cos(radians(:lat)) * cos(radians(p.latitude)) * " +
           "cos(radians(p.longitude) - radians(:lng)) + sin(radians(:lat)) * " +
           "sin(radians(p.latitude)))) ASC")
    List<PontoEncontro> findMaisProximos(@Param("lat") double lat, @Param("lng") double lng);
}

// ---- Clube ---------------------------------------------------------

@Repository
interface ClubeRepository extends JpaRepository<Clube, UUID> {
    @Query("SELECT c FROM Clube c JOIN c.membros m WHERE m.id = :uid")
    List<Clube> findByMembroId(@Param("uid") UUID usuarioId);
}

// ---- ListaDesejo ---------------------------------------------------

@Repository
interface ListaDesejoRepository extends JpaRepository<ListaDesejo, UUID> {
    Optional<ListaDesejo> findByUsuarioId(UUID usuarioId);
}

// ---- Avaliacao -----------------------------------------------------

@Repository
interface AvaliacaoRepository extends JpaRepository<Avaliacao, UUID> {
    List<Avaliacao> findByAvaliadoId(UUID avaliadoId);
    List<Avaliacao> findByReferenciaId(UUID referenciaId);

    @Query("SELECT AVG(a.nota) FROM Avaliacao a WHERE a.referenciaId = :ref AND a.tipo = :tipo")
    Double mediaNotaPor(@Param("ref") UUID referenciaId, @Param("tipo") TipoAvaliacao tipo);
}

// ---- Notificacao ---------------------------------------------------

@Repository
interface NotificacaoRepository extends JpaRepository<Notificacao, UUID> {
    List<Notificacao> findByDestinatarioIdOrderByCriadoEmDesc(UUID destinatarioId);
    List<Notificacao> findByDestinatarioIdAndLidaFalse(UUID destinatarioId);
    long countByDestinatarioIdAndLidaFalse(UUID destinatarioId);
}

// ---- ChatMensagem --------------------------------------------------

@Repository
interface ChatMensagemRepository extends JpaRepository<ChatMensagem, UUID> {
    @Query("SELECT m FROM ChatMensagem m WHERE " +
           "(m.remetente.id = :a AND m.destinatario.id = :b) OR " +
           "(m.remetente.id = :b AND m.destinatario.id = :a) " +
           "ORDER BY m.enviadoEm ASC")
    List<ChatMensagem> findConversa(@Param("a") UUID usuarioA, @Param("b") UUID usuarioB);
}