package com.edimilsonborges.forum_hub.dto;

import com.edimilsonborges.forum_hub.model.Categoria;
import com.edimilsonborges.forum_hub.model.Curso;

import java.util.List;
public record DadosCursos(Long id, String nome, Categoria categoria, List<Curso>cursos) {
}
