package com.edimilsonborges.forum_hub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
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
    @JsonIgnore
    @ManyToOne
    private Usuario usuario;
    @JsonIgnore
    @ManyToOne
    private Curso curso;
}
