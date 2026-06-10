package com.folhear.entity;

import com.folhear.entity.enums.TipoPonto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "ponto_encontro")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PontoEncontro {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 300)
    private String endereco;

    @Column(nullable = false)
    private Float latitude;

    @Column(nullable = false)
    private Float longitude;

    @Column(name = "horario_funcionamento", length = 255)
    private String horarioFuncionamento;

    @Column(name = "nota_media")
    private Float notaMedia = 0f;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoPonto tipo = TipoPonto.OUTRO;
}