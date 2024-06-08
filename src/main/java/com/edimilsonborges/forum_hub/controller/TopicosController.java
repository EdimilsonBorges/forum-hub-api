package com.edimilsonborges.forum_hub.controller;

import com.edimilsonborges.forum_hub.model.*;
import com.edimilsonborges.forum_hub.repository.CursoRepisitory;
import com.edimilsonborges.forum_hub.repository.TopicoRepository;
import com.edimilsonborges.forum_hub.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private CursoRepisitory cursoRepisitory;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    @PostMapping
    public Topico CadastrarTopico(@RequestBody @Valid DadosCadastroTopicos dadosCadastroTopicos) {

        Curso curso = cursoRepisitory.findByNome(dadosCadastroTopicos.curso());
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(1L);

        if (curso == null || optionalUsuario.isEmpty()) {
            throw new RuntimeException("Curso ou usuario nao existe");
        }
        Usuario usuario = optionalUsuario.get();

        Topico topico = new Topico(null, dadosCadastroTopicos.titulo(), dadosCadastroTopicos.mensagem(), LocalDateTime.now(), Status.NAO_RESOLVIDO, usuario, curso);
        topicoRepository.save(topico);
        return topico;

    }

    @GetMapping
    public Page<DadosListagemTopicos> listarTodosTopicos(@PageableDefault(size = 10, sort = {"dataCriacao"}) Pageable paginacao) {

        return topicoRepository.findAll(paginacao)
                .map(t -> new DadosListagemTopicos(
                        t.getTitulo(),
                        t.getMensagem(),
                        t.getDataCriacao(),
                        t.getStatus(),
                        t.getUsuario().getNome(),
                        t.getCurso().getNome()));
    }
}
