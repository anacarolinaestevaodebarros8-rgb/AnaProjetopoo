package com.folhear.entity;

import com.folhear.entity.enums.GeneroLivro;
import com.folhear.entity.enums.TipoObra;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "obra_autoral")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ObraAutoral {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    @Column(nullable = false, length = 255)
    private String titulo;

    @Column(length = 500)
    private String capa;

    @Column(columnDefinition = "TEXT")
    private String sinopse;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private GeneroLivro genero;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_obra", nullable = false, length = 20)
    private TipoObra tipoObra = TipoObra.OUTRO;

    private Float preco = 0f;

    @Column(name = "nota_media")
    private Float notaMedia = 0f;

    private Integer visualizacoes = 0;

    @Column(name = "leitores_unicos")
    private Integer leitoresUnicos = 0;

    @Column(name = "destaque_local")
    private Boolean destaqueLocal = false;

    @Column(name = "publicado_em")
    private LocalDateTime publicadoEm;

    @OneToMany(mappedBy = "obra", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("numero ASC")
    private List<Capitulo> capitulos = new ArrayList<>();
}