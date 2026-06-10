package com.folhear.entity;

import com.folhear.entity.enums.TipoUsuario;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuario")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Usuario {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "senha_hash", nullable = false, length = 255)
    private String senhaHash;

    @Column(unique = true, length = 14)
    private String cpf;

    @Column(length = 100)
    private String cidade;

    @Column(name = "foto_perfil", length = 500)
    private String fotoPerfil;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoUsuario tipo = TipoUsuario.LEITOR;

    @Column(name = "nota_media")
    private Float notaMedia = 0f;

    @Column(name = "total_transacoes")
    private Integer totalTransacoes = 0;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

    // ---- Relacionamentos ----

    @ElementCollection
    @CollectionTable(name = "interesses_literarios",
            joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "interesse")
    private List<String> interessesLiterarios = new ArrayList<>();

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ListaDesejo listaDesejo;

    @OneToMany(mappedBy = "vendedor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Livro> livros = new ArrayList<>();

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ObraAutoral> obras = new ArrayList<>();

    @ManyToMany(mappedBy = "membros", fetch = FetchType.LAZY)
    private List<Clube> clubes = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        criadoEm = LocalDateTime.now();
    }
}