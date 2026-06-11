package com.folhear.entity;

import com.folhear.entity.enums.StatusTroca;
import javax.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "troca")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Troca {

    @Id
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proponente_id", nullable = false)
    private Usuario proponente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receptor_id", nullable = false)
    private Usuario receptor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ponto_encontro_id")
    private PontoEncontro pontoEncontro;

    @Column(name = "data_agendamento")
    private LocalDateTime dataAgendamento;

    @Column(name = "qr_code_checkin", length = 255)
    private String qrCodeCheckin;

    private Float complemento = 0f;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusTroca status = StatusTroca.PROPOSTA;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transacao_id", unique = true)
    private Transacao transacao;

    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

    // Itens oferecidos (lado = 'O') e solicitados (lado = 'S')
    @OneToMany(mappedBy = "troca", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrocaItem> itens = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        criadoEm = LocalDateTime.now();
    }
}