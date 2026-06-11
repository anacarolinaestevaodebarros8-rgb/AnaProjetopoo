package com.folhear.entity;

import com.folhear.entity.enums.EstadoConservacao;
import com.folhear.entity.enums.TipoAnuncio;
import javax.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "livro")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Livro {

    @Id
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, length = 255)
    private String titulo;

    @Column(nullable = false, length = 150)
    private String autor;

    @Column(length = 20)
    private String isbn;

    @Column(length = 100)
    private String categoria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoConservacao estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_anuncio", nullable = false, length = 10)
    private TipoAnuncio tipoAnuncio;

    private Float preco;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(name = "expiracao_em")
    private LocalDateTime expiracaoEm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendedor_id", nullable = false)
    private Usuario vendedor;

    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

    @ElementCollection
    @CollectionTable(name = "livro_fotos",
            joinColumns = @JoinColumn(name = "livro_id"))
    @Column(name = "url_foto")
    private List<String> fotos = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        criadoEm = LocalDateTime.now();
    }
}