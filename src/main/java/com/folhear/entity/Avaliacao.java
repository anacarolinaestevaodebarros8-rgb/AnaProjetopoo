package com.folhear.entity;

import com.folhear.entity.enums.TipoAvaliacao;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "avaliacao",
       uniqueConstraints = @UniqueConstraint(columnNames = {"avaliador_id","referencia_id","tipo"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Avaliacao {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avaliador_id", nullable = false)
    private Usuario avaliador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avaliado_id", nullable = false)
    private Usuario avaliado;

    @Column(name = "referencia_id", columnDefinition = "uuid", nullable = false)
    private UUID referenciaId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoAvaliacao tipo;

    @Column(nullable = false)
    private Integer nota;

    @Column(columnDefinition = "TEXT")
    private String comentario;

    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

    @PrePersist
    protected void onCreate() {
        criadoEm = LocalDateTime.now();
    }
}