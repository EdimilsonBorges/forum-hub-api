package com.edimilsonborges.forum_hub.dto.cursos;

import com.edimilsonborges.forum_hub.models.Categoria;
import com.edimilsonborges.forum_hub.models.Curso;
import org.springframework.hateoas.Links;

import java.util.UUID;

public record DadosListagemCurso(UUID id, String nome, Categoria categoria, Links links) {
    public DadosListagemCurso(Curso curso) {
        this(curso.getId(), curso.getNome(), curso.getCategoria(), curso.getLinks());

    }
}
