package com.folhear.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "troca_item",
       uniqueConstraints = @UniqueConstraint(columnNames = {"troca_id","livro_id","lado"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TrocaItem {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "troca_id", nullable = false)
    private Troca troca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;

    /** 'O' = Oferecido, 'S' = Solicitado */
    @Column(nullable = false, length = 1)
    private Character lado;
}