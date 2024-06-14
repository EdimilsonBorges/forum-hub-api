package com.edimilsonborges.forum_hub.models;

import com.edimilsonborges.forum_hub.dto.respostas.DadosAtualizacaoResposta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "respostas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Resposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String mensagem;
    private LocalDateTime dataCriacao;
    @Setter
    @Column(nullable = false)
    private Boolean solucao;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario usuario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Topico topico;

    public Resposta(String mensagem, Topico topico){
        this.mensagem = mensagem;
        this.usuario = Usuario.getUsuarioLogado();
        this.topico = topico;
        this.solucao = false;
        this.dataCriacao = LocalDateTime.now();
    }

    public void atualizarInformacoes(DadosAtualizacaoResposta dadosAtualizacaoResposta) {
        if (dadosAtualizacaoResposta.mensagem() != null) {
            this.mensagem = dadosAtualizacaoResposta.mensagem();
        }
    }
}
