package com.folhear.entity;

import com.folhear.entity.enums.StatusTransacao;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transacao")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Transacao {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comprador_id", nullable = false)
    private Usuario comprador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendedor_id", nullable = false)
    private Usuario vendedor;

    @Column(name = "valor_bruto", nullable = false)
    private Float valorBruto;

    private Float comissao = 0f;

    @Column(name = "valor_liquido")
    private Float valorLiquido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusTransacao status = StatusTransacao.PENDENTE;

    @Column(name = "codigo_rastreamento", length = 100)
    private String codigoRastreamento;

    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "liberado_em")
    private LocalDateTime liberadoEm;

    @OneToOne(mappedBy = "transacao", fetch = FetchType.LAZY)
    private Troca troca;

    @PrePersist
    protected void onCreate() {
        criadoEm = LocalDateTime.now();
        if (valorLiquido == null && valorBruto != null) {
            valorLiquido = valorBruto - (comissao != null ? comissao : 0f);
        }
    }
}