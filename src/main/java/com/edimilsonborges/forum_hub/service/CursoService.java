package com.edimilsonborges.forum_hub.service;

import com.edimilsonborges.forum_hub.controller.CursoController;
import com.edimilsonborges.forum_hub.dto.cursos.DadosAtualizacaoCurso;
import com.edimilsonborges.forum_hub.dto.cursos.DadosListagemCurso;
import com.edimilsonborges.forum_hub.dto.cursos.DadosCadastroCurso;
import com.edimilsonborges.forum_hub.dto.status.DadosErros;
import com.edimilsonborges.forum_hub.dto.status.DadosSucesso;
import com.edimilsonborges.forum_hub.models.Categoria;
import com.edimilsonborges.forum_hub.models.Curso;
import com.edimilsonborges.forum_hub.repositories.CursoRepisitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class CursoService {
    @Autowired
    CursoRepisitory cursoRepisitory;

    public ResponseEntity<?> cadastrarCurso(DadosCadastroCurso dadosCadastroCurso, UriComponentsBuilder uriComponentsBuilder) {
        try {
            Curso curso = cursoRepisitory.save(new Curso(dadosCadastroCurso));
            curso.add(linkTo(methodOn(CursoController.class).listarTodosCursos(Pageable.unpaged())).withRel("Lista de cursos"));
            URI uri = uriComponentsBuilder.path("cursos/{id}").buildAndExpand(curso.getId()).toUri();
            return ResponseEntity.created(uri).body(new DadosListagemCurso(curso));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DadosErros("[" + dadosCadastroCurso.categoria() + "] é inválido, os valores válidos são: " + Arrays.toString(Categoria.values())));
        }
    }

    public ResponseEntity<Page<DadosListagemCurso>> listarTodosCursos(Pageable pageable) {
        Page<DadosListagemCurso> page = cursoRepisitory.findAll(pageable)
                .map(curso -> {
                    curso.add(linkTo(methodOn(CursoController.class).listarCurso(curso.getId())).withRel("Detalhes do curso"));
                    return new DadosListagemCurso(curso);
                });
        return ResponseEntity.ok(page);
    }

    public ResponseEntity<?> listarCurso(UUID id) {
        if (!cursoRepisitory.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DadosErros("Curso não encontrado"));
        }
        Curso curso = cursoRepisitory.getReferenceById(id);
        curso.add(linkTo(methodOn(CursoController.class).listarTodosCursos(Pageable.unpaged())).withRel("Lista de cursos"));
        return ResponseEntity.ok(new DadosListagemCurso(curso));
    }

    public ResponseEntity<?> autualizarCurso(DadosAtualizacaoCurso dadosAtualizacaoCurso, UUID id) {
        Optional<Curso> optionalCurso = cursoRepisitory.findById(id);
        if (optionalCurso.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DadosErros("Curso não encontrado"));
        }
        Curso curso = optionalCurso.get();
        curso.add(linkTo(methodOn(CursoController.class).listarTodosCursos(Pageable.unpaged())).withRel("Lista de cursos"));
        try {
            curso.atualizarInformacoes(dadosAtualizacaoCurso);
            return ResponseEntity.ok(new DadosListagemCurso(curso));
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DadosErros("[" + dadosAtualizacaoCurso.categoria() + "] é inválido, os valores válidos são: " + Arrays.toString(Categoria.values())));
        }
    }

    public ResponseEntity<?> excluirCurso(UUID id) {
        Optional<Curso> optionalCurso = cursoRepisitory.findById(id);

        if (optionalCurso.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DadosErros("Curso não encontrado para a exclusão!"));
        }

        cursoRepisitory.deleteById(id);
        return ResponseEntity.ok(new DadosSucesso("Curso excluído com sucesso!"));
    }
}
