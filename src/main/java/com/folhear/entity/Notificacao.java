package com.folhear.entity;

import com.folhear.entity.enums.TipoNotificacao;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Notificações do sistema para usuários
 */
@Entity
@Table(name = "notificacao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notificacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoNotificacao tipo;

    @Column(columnDefinition = "TEXT")
    private String mensagem;

    @Column(nullable = false)
    private Boolean lida = false;

    @Column(nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    private LocalDateTime dataLeitura;

    // Referências opcionais para diferentes tipos
    private Long trocaId;
    private Long transacaoId;
    private Long chatMensagemId;
    private Long clubeId;
}
