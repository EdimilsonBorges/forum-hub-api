package com.edimilsonborges.forum_hub.models;

import com.edimilsonborges.forum_hub.dto.DadosAtualizacaoTopico;
import com.edimilsonborges.forum_hub.dto.DadosCadastroTopicos;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Entity(name = "Topico")
@Table(name = "topicos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String titulo;
    @Column(unique = true, nullable = false)
    private String mensagem;
    private LocalDateTime dataCriacao;
    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;
    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resposta> respostas;

    public Topico(String titulo, String mensagem,Curso curso){
        this.id = null;
        this.titulo = titulo;
        this.mensagem = mensagem;
        this.dataCriacao = LocalDateTime.now();
        this.status = Status.NAO_RESOLVIDO;
        this.usuario = Usuario.getUsuarioLogado();
        this.curso = curso;
    }

    public void atualizarInformacoes(DadosAtualizacaoTopico dadosAtualizacaoTopico) {
        Optional<String> optionalTitulo = Optional.ofNullable(dadosAtualizacaoTopico.titulo());
        optionalTitulo.ifPresent(t -> this.titulo = t);

        Optional<String> optionalMensagem = Optional.ofNullable(dadosAtualizacaoTopico.mensagem());
        optionalMensagem.ifPresent(m -> this.mensagem = m);
    }
}