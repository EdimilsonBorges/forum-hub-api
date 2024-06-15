package com.edimilsonborges.forum_hub.models;

import com.edimilsonborges.forum_hub.dto.topicos.DadosAtualizacaoTopico;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity(name = "Topico")
@Table(name = "topicos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
public class Topico extends RepresentationModel<Topico> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
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