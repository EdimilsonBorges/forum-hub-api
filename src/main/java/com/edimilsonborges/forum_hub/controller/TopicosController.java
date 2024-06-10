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
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.Optional;
import java.util.stream.IntStream;

@RestController
@RequestMapping("topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private CursoRepisitory cursoRepisitory;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @PostMapping
    @Transactional
    public ResponseEntity<?> CadastrarTopico(@RequestBody @Valid DadosCadastroTopicos dadosCadastroTopicos, UriComponentsBuilder uriBuilder) {

        Curso curso = cursoRepisitory.findByNome(dadosCadastroTopicos.curso());
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(1L);

        if (curso == null || optionalUsuario.isEmpty()) {
            throw new RuntimeException("Curso ou usuario nao existe");
        }
        Usuario usuario = optionalUsuario.get();

        Topico topico = new Topico(dadosCadastroTopicos, usuario, curso);
        topicoRepository.save(topico);

        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosListagemTopicos(topico));

    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemTopicos>> listarTodosTopicos(@PageableDefault(size = 10, sort = {"dataCriacao"}) Pageable paginacao) {

        Page<DadosListagemTopicos> page = topicoRepository.findAll(paginacao)
                .map(t -> new DadosListagemTopicos(
                        t.getId(),
                        t.getTitulo(),
                        t.getMensagem(),
                        t.getDataCriacao(),
                        t.getStatus(),
                        t.getUsuario().getNome(),
                        t.getCurso().getNome()));

        return ResponseEntity.ok(page);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DadosListagemTopicos> listarTopico(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(new DadosListagemTopicos(topicoRepository.getReferenceById(id)));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> atualizarTopico(@RequestBody @Valid DadosAtualizacaoTopico dadosAtualizacaoTopico, @PathVariable(name = "id") Long id){
        Topico topico = topicoRepository.getReferenceById(id);
        topico.atualizarInformacoes(dadosAtualizacaoTopico);

        return ResponseEntity.ok(new DadosListagemTopicos(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> excluirTopico(@PathVariable(name = "id") Long id){
        if(topicoRepository.existsById(id)){
            topicoRepository.deleteById(id);
        }
        return ResponseEntity.noContent().build();
    }
}
