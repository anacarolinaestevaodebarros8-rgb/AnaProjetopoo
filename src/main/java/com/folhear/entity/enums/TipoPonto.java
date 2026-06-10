package com.folhear.entity.enums;

/**
 * Tipo de ponto de encontro: BIBLIOTECA, LIVRARIA, CAFE, OUTRO
 */
public enum TipoPonto {
    BIBLIOTECA("Biblioteca"),
    LIVRARIA("Livraria"),
    CAFE("Café"),
    OUTRO("Outro");

    private final String descricao;

    TipoPonto(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
