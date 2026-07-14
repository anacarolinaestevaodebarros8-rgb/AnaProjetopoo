package com.folhear.service;

import com.folhear.entity.Livro;
import com.folhear.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public Livro criarLivro(Livro livro) {
        if (livro.getId() == null) {
            livro.setId(UUID.randomUUID());
        }
        return livroRepository.save(livro);
    }

    public Livro buscarPorId(UUID id) {
        return livroRepository.findById(id).orElse(null);
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public Livro atualizar(UUID id, Livro dadosAtualizados) {
        Livro livroExistente = buscarPorId(id);
        if (livroExistente == null) {
            return null;
        }
        dadosAtualizados.setId(id);
        return livroRepository.save(dadosAtualizados);
    }

    public boolean deletar(UUID id) {
        if (!livroRepository.existsById(id)) {
            return false;
        }
        livroRepository.deleteById(id);
        return true;
    }
}