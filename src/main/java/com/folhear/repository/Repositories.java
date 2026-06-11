package com.folhear.repository;

import com.folhear.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Todas os JpaRepository em um arquivo.
 * Em produção, considere mover cada um para seu próprio arquivo.
 */

// UsuarioRepository
interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}

// LivroRepository
interface LivroRepository extends JpaRepository<Livro, Long> {
}

// TrocaRepository
interface TrocaRepository extends JpaRepository<Troca, Long> {
}

// TransacaoRepository
interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}

// ObraAutorialRepository
interface ObraAutorialRepository extends JpaRepository<ObraAutoral, Long> {
}

// CapituloRepository
interface CapituloRepository extends JpaRepository<Capitulo, Long> {
}

// PontoEncontroRepository
interface PontoEncontroRepository extends JpaRepository<PontoEncontro, Long> {
}

// ClubeRepository
interface ClubeRepository extends JpaRepository<Clube, Long> {
}

// ListaDesejoRepository
interface ListaDesejoRepository extends JpaRepository<ListaDesejo, Long> {
}

// AvaliacaoRepository
interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
}

// NotificacaoRepository
interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
}

// ChatMensagemRepository
interface ChatMensagemRepository extends JpaRepository<ChatMensagem, Long> {
}

// RemessaRepository
interface RemessaRepository extends JpaRepository<Remessa, Long> {
}

// TrocaItemRepository
interface TrocaItemRepository extends JpaRepository<TrocaItem, Long> {
}
