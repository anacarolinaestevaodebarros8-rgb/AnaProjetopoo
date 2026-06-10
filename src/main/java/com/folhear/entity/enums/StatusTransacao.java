package com.folhear.entity.enums;

/**
 * Status de uma transação: PENDENTE, PAGO, ENVIADO, ENTREGUE, CANCELADO, DISPUTA, CONCLUIDO
 */
public enum StatusTransacao {
    PENDENTE("Pendente"),
    PAGO("Pago"),
    ENVIADO("Enviado"),
    ENTREGUE("Entregue"),
    CANCELADO("Cancelado"),
    DISPUTA("Disputa"),
    CONCLUIDO("Concluído");

    private final String descricao;

    StatusTransacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
