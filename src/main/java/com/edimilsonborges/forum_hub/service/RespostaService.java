package com.edimilsonborges.forum_hub.service;

import com.edimilsonborges.forum_hub.dto.respostas.DadosAtualizacaoResposta;
import com.edimilsonborges.forum_hub.dto.respostas.DadosCadastroResposta;
import com.edimilsonborges.forum_hub.dto.respostas.DadosListagemResposta;
import com.edimilsonborges.forum_hub.dto.status.DadosErros;
import com.edimilsonborges.forum_hub.dto.status.DadosSucesso;
import com.edimilsonborges.forum_hub.models.Resposta;
import com.edimilsonborges.forum_hub.models.Topico;
import com.edimilsonborges.forum_hub.models.Usuario;
import com.edimilsonborges.forum_hub.repositories.RespostaRepository;
import com.edimilsonborges.forum_hub.repositories.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
public class RespostaService {

    @Autowired
    RespostaRepository respostaRepository;
    @Autowired
    TopicoRepository topicoRepository;
    public ResponseEntity<?> cadastrarResposta(DadosCadastroResposta dadosCadastroResposta, UriComponentsBuilder uriComponentsBuilder) {
        Optional<Topico> optionalTopico = topicoRepository.findById(dadosCadastroResposta.topicoId());
        if(optionalTopico.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DadosErros("Tópico não encontrado!"));
        }

        Topico topico = optionalTopico.get();
        Resposta resposta = new Resposta(dadosCadastroResposta.mensagem(),topico);
        respostaRepository.save(resposta);

        URI uri = uriComponentsBuilder.path("respostas/{id}").buildAndExpand(resposta.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosListagemResposta(resposta));
    }

    public ResponseEntity<Page<DadosListagemResposta>> listarTodasRespostas(Pageable pageable) {
        Page<DadosListagemResposta> page = respostaRepository.findAll(pageable)
                .map(DadosListagemResposta::new);
        return ResponseEntity.ok().body(page);
    }

    public ResponseEntity<?> listarResposta(Long id) {
        Optional<Resposta> optionalResposta = respostaRepository.findById(id);
        if(optionalResposta.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DadosErros("Resposta não encontrada!"));
        }
        Resposta resposta = optionalResposta.get();
        return ResponseEntity.ok(new DadosListagemResposta(resposta));
    }

    public ResponseEntity<?> atualizarResposta(DadosAtualizacaoResposta dadosAtualizacaoResposta, Long id) {
        Optional<Resposta> optionalResposta = respostaRepository.findById(id);
        if(optionalResposta.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DadosErros("Resposta não encontrada para ser atualizada!"));
        }

        Resposta resposta = optionalResposta.get();
        if(!Usuario.temPermisaoParaModificacao(resposta.getUsuario())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DadosErros("Você não pode modificar a resposta de outra pessoa!"));
        }

        resposta.atualizarInformacoes(dadosAtualizacaoResposta);

        return ResponseEntity.ok(new DadosListagemResposta(resposta));
    }

    public ResponseEntity<?> excluirResposta(Long id) {
        Optional<Resposta> optionalResposta = respostaRepository.findById(id);

        if(optionalResposta.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DadosErros("Resposta não encontrado para a exclusão!"));
        }
        Resposta resposta = optionalResposta.get();
        if(!Usuario.temPermisaoParaModificacao(resposta.getUsuario())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new DadosErros("Você não tem permissão para excluir a resposta de outra pessoa!"));
        }

        respostaRepository.deleteById(id);

        return ResponseEntity.ok(new DadosSucesso("Resposta excluído com sucesso!"));
    }
}
