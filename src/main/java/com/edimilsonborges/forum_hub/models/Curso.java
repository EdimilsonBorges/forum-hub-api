package com.edimilsonborges.forum_hub.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity(name = "Cursos")
@Table(name = "cursos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Embeddable
public class Curso extends RepresentationModel<Curso> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topico> topicos;
}
