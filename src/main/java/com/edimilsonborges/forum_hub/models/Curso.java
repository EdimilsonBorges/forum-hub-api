package com.edimilsonborges.forum_hub.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity(name = "Cursos")
@Table(name = "cursos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Embeddable
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topico> topicos;
}
