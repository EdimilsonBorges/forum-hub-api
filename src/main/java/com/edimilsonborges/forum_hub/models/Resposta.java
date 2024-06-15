package com.edimilsonborges.forum_hub.models;

import com.edimilsonborges.forum_hub.dto.respostas.DadosAtualizacaoResposta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "respostas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Resposta extends RepresentationModel<Curso> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
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
