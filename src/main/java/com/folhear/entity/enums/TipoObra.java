package com.folhear.entity.enums;

/**
 * Tipo de obra autoral: ROMANCE, CONTO, POESIA, CRONICA, OUTRO
 */
public enum TipoObra {
    ROMANCE("Romance"),
    CONTO("Conto"),
    POESIA("Poesia"),
    CRONICA("Crônica"),
    OUTRO("Outro");

    private final String descricao;

    TipoObra(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
