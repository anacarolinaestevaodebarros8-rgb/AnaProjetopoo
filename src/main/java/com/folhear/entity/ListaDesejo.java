package com.folhear.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "lista_desejo")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ListaDesejo {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", unique = true, nullable = false)
    private Usuario usuario;

    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

    @OneToMany(mappedBy = "listaDesejo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ListaDesejoItem> itens = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        criadoEm = LocalDateTime.now();
    }
}