package com.edimilsonborges.forum_hub.service;

import com.edimilsonborges.forum_hub.dto.*;
import com.edimilsonborges.forum_hub.models.Curso;
import com.edimilsonborges.forum_hub.models.Topico;
import com.edimilsonborges.forum_hub.models.Usuario;
import com.edimilsonborges.forum_hub.repositories.CursoRepisitory;
import com.edimilsonborges.forum_hub.repositories.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

@Service
public class TopicoService {
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private CursoRepisitory cursoRepisitory;

    public ResponseEntity<?> cadastrarTopico(DadosCadastroTopicos dadosCadastroTopicos, UriComponentsBuilder uriBuilder) {
        Curso curso = cursoRepisitory.findByNome(dadosCadastroTopicos.curso());

        if (curso == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DadosErros("Curso não encontrado!"));
        }

        Topico topico = new Topico(dadosCadastroTopicos, getUsuarioLogado(), curso);
        topicoRepository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosListagemTopicos(topico));
    }

    public ResponseEntity<Page<DadosListagemTopicos>> listarTodosTopicos(Pageable paginacao) {
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

    public ResponseEntity<DadosListagemTopicos> listarTopico(Long id) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);
        return optionalTopico.map(topico -> ResponseEntity.ok(new DadosListagemTopicos(topico))).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public ResponseEntity<?> atualizarTopico(DadosAtualizacaoTopico dadosAtualizacaoTopico, Long id) {

        Optional<Topico> optionalTopico = topicoRepository.findById(id);

        if (optionalTopico.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DadosErros("Tópico não existe!"));
        }

        if (!temPermisaoParaModificacao(optionalTopico.get())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new DadosErros("Você não tem permissão para modificar tópicos de outra pessoa!"));
        }

        Topico topico = optionalTopico.get();
        if(!topico.atualizarInformacoes(dadosAtualizacaoTopico)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new DadosErros("Status inválido!"));
        }

        return ResponseEntity.ok(new DadosListagemTopicos(topico));
    }

    public ResponseEntity<?> excluirTopico(Long id) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);

        if (optionalTopico.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DadosErros("Tópico não encontrado para a exclusão!"));
        }

        if (!temPermisaoParaModificacao(optionalTopico.get())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new DadosErros("Você não tem permissão para excluir tópicos de outra pessoa!"));
        }

        topicoRepository.deleteById(id);

        return ResponseEntity.ok(new DadosSucesso("Tópico excluído com sucesso!"));
    }

    private Usuario getUsuarioLogado() {
        return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private boolean temPermisaoParaModificacao(Topico topico) {
        return Objects.equals(getUsuarioLogado().getId(), topico.getUsuario().getId());
    }


}
