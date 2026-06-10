package com.folhear.entity.enums;

/**
 * Estado de conservação do livro: OTIMO, BOM, REGULAR, USADO
 */
public enum EstadoConservacao {
    OTIMO("Ótimo"),
    BOM("Bom"),
    REGULAR("Regular"),
    USADO("Usado");

    private final String descricao;

    EstadoConservacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
