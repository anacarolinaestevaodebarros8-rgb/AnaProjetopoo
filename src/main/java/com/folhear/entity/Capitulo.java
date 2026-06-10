package com.folhear.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "capitulo")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Capitulo {

    @Id
    @UuidGenerator(name = "UUID")
    @GeneratedValue(generator = "UUID")
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "obra_id", nullable = false)
    private ObraAutoral obra;

    @Column(nullable = false)
    private Integer numero;

    @Column(length = 255)
    private String titulo;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String conteudo;

    @Column(nullable = false)
    private Boolean gratuito = false;

    @Column(name = "publicado_em")
    private LocalDateTime publicadoEm;
}