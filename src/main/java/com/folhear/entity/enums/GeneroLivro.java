package com.folhear.entity.enums;

/**
 * Gênero de livro: FICCAO, NAO_FICCAO, FANTASIA, MISTERIO, ...
 */
public enum GeneroLivro {
    FICCAO("Ficção"),
    NAO_FICCAO("Não-Ficção"),
    FANTASIA("Fantasia"),
    MISTERIO("Mistério"),
    ROMANCE("Romance"),
    SUSPENSE("Suspense"),
    CIENCIA_FICCAO("Ficção Científica"),
    HISTORIA("História"),
    BIOGRAFIA("Biografia"),
    AUTOAJUDA("Autoajuda"),
    INFANTIL("Infantil"),
    POESIA("Poesia"),
    DRAMA("Drama"),
    COMEDIA("Comédia"),
    OUTRO("Outro");

    private final String descricao;

    GeneroLivro(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
