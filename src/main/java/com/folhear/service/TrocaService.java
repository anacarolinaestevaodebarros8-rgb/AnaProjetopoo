package com.folhear.service;

import com.folhear.entity.*;
import com.folhear.entity.enums.StatusTroca;
import com.folhear.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrocaService {

    private final TrocaRepository trocaRepository;
    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;
    private final PontoEncontroRepository pontoEncontroRepository;

    public List<Troca> minhasTrocas(UUID usuarioId) {
        return trocaRepository.findByParticipante(usuarioId);
    }

    @Transactional
    public Troca propor(UUID proponenteId, UUID receptorId,
                        List<UUID> livrosOferecidosIds, List<UUID> livrosSolicitadosIds,
                        UUID pontoEncontroId, Float complemento) {

        Usuario proponente = usuarioRepository.findById(proponenteId)
                .orElseThrow(() -> new EntityNotFoundException("Proponente não encontrado"));
        Usuario receptor = usuarioRepository.findById(receptorId)
                .orElseThrow(() -> new EntityNotFoundException("Receptor não encontrado"));

        Troca troca = Troca.builder()
                .proponente(proponente)
                .receptor(receptor)
                .complemento(complemento != null ? complemento : 0f)
                .status(StatusTroca.PROPOSTA)
                .build();

        if (pontoEncontroId != null) {
            PontoEncontro ponto = pontoEncontroRepository.findById(pontoEncontroId)
                    .orElseThrow(() -> new EntityNotFoundException("Ponto de encontro não encontrado"));
            troca.setPontoEncontro(ponto);
        }

        troca = trocaRepository.save(troca);

        // Adicionar itens oferecidos
        for (UUID livroId : livrosOferecidosIds) {
            Livro livro = livroRepository.findById(livroId)
                    .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado: " + livroId));
            troca.getItens().add(TrocaItem.builder()
                    .troca(troca).livro(livro).lado('O').build());
        }

        // Adicionar itens solicitados
        for (UUID livroId : livrosSolicitadosIds) {
            Livro livro = livroRepository.findById(livroId)
                    .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado: " + livroId));
            troca.getItens().add(TrocaItem.builder()
                    .troca(troca).livro(livro).lado('S').build());
        }

        return trocaRepository.save(troca);
    }

    @Transactional
    public Troca aceitar(UUID trocaId, UUID receptorId) {
        return mudarStatus(trocaId, receptorId, StatusTroca.ACEITA);
    }

    @Transactional
    public Troca recusar(UUID trocaId, UUID receptorId) {
        return mudarStatus(trocaId, receptorId, StatusTroca.RECUSADA);
    }

    @Transactional
    public Troca contrapropor(UUID trocaId, UUID receptorId) {
        return mudarStatus(trocaId, receptorId, StatusTroca.CONTRAPROPOSTA);
    }

    private Troca mudarStatus(UUID trocaId, UUID usuarioId, StatusTroca novoStatus) {
        Troca troca = trocaRepository.findById(trocaId)
                .orElseThrow(() -> new EntityNotFoundException("Troca não encontrada: " + trocaId));

        boolean isParticipante = troca.getProponente().getId().equals(usuarioId)
                              || troca.getReceptor().getId().equals(usuarioId);
        if (!isParticipante) {
            throw new SecurityException("Acesso negado a esta troca.");
        }

        troca.setStatus(novoStatus);
        return trocaRepository.save(troca);
    }
}