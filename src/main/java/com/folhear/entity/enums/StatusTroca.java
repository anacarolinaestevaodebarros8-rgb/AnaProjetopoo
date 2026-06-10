package com.folhear.entity.enums;

/**
 * Status de uma proposta de troca: PROPOSTA, ACEITA, RECUSADA, CONTRAPROPOSTA, CONCLUIDA
 */
public enum StatusTroca {
    PROPOSTA("Proposta"),
    ACEITA("Aceita"),
    RECUSADA("Recusada"),
    CONTRAPROPOSTA("Contraproposta"),
    CONCLUIDA("Concluída");

    private final String descricao;

    StatusTroca(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
