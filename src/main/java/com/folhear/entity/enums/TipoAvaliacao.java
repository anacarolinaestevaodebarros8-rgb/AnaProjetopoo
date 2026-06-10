package com.folhear.entity.enums;

/**
 * Tipo de avaliação: LIVRO, USUARIO, PONTO_ENCONTRO
 */
public enum TipoAvaliacao {
    LIVRO("Livro"),
    USUARIO("Usuário"),
    PONTO_ENCONTRO("Ponto de Encontro");

    private final String descricao;

    TipoAvaliacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
