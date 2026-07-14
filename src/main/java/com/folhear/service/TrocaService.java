package com.folhear.service;

import com.folhear.entity.Troca;
import com.folhear.repository.TrocaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TrocaService {

    @Autowired
    private TrocaRepository trocaRepository;

    public Troca criarTroca(Troca troca) {
        if (troca.getId() == null) {
            troca.setId(UUID.randomUUID());
        }
        return trocaRepository.save(troca);
    }

    public Troca buscarPorId(UUID id) {
        return trocaRepository.findById(id).orElse(null);
    }

    public List<Troca> listarTodas() {
        return trocaRepository.findAll();
    }

    public Troca atualizar(UUID id, Troca dadosAtualizados) {
        Troca trocaExistente = buscarPorId(id);
        if (trocaExistente == null) {
            return null;
        }
        dadosAtualizados.setId(id);
        return trocaRepository.save(dadosAtualizados);
    }

    public boolean deletar(UUID id) {
        if (!trocaRepository.existsById(id)) {
            return false;
        }
        trocaRepository.deleteById(id);
        return true;
    }
}
