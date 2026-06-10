package com.folhear.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Mensagens de chat entre usuários
 */
@Entity
@Table(name = "chat_mensagem")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMensagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_remetente_id", nullable = false)
    private Usuario remetente;

    @ManyToOne
    @JoinColumn(name = "usuario_destinatario_id", nullable = false)
    private Usuario destinatario;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String conteudo;

    @Column(nullable = false)
    private LocalDateTime dataEnvio = LocalDateTime.now();

    @Column(nullable = false)
    private Boolean lida = false;

    private LocalDateTime dataLeitura;

    // Referência opcional para conversa relacionada (ex: troca, venda)
    private Long trocaId;
    private Long transacaoId;
}
