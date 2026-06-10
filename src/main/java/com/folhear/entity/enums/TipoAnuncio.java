package com.folhear.entity.enums;

/**
 * Tipo de anúncio de livro: VENDA, TROCA ou AMBOS
 */
public enum TipoAnuncio {
    VENDA("Venda"),
    TROCA("Troca"),
    AMBOS("Venda e Troca");

    private final String descricao;

    TipoAnuncio(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
