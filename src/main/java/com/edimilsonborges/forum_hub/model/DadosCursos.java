package com.edimilsonborges.forum_hub.model;

import java.util.List;
public record DadosCursos(Long id, String nome, Categoria categoria, List<Curso>cursos) {
}
