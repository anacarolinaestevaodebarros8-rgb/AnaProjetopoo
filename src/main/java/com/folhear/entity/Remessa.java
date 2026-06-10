package com.folhear.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Informações de envio/frete de uma transação
 */
@Entity
@Table(name = "remessa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Remessa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "transacao_id", nullable = false)
    private Transacao transacao;

    @Column(nullable = false)
    private String codigoRastreamento;

    @Column(nullable = false)
    private String transportadora;

    @Column(columnDefinition = "TEXT")
    private String endereco;

    @Column
    private String cep;

    @Column
    private String cidade;

    @Column
    private String estado;

    @Column
    private Double pesoKg;

    @Column
    private Double custoPosto;

    @Column
    private LocalDate dataPostagem;

    @Column
    private LocalDate dataEntregaPrevista;

    @Column
    private LocalDate dataEntregaReal;

    @Column(columnDefinition = "TEXT")
    private String statusRastreamento;
}
