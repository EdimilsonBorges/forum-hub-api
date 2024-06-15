package com.edimilsonborges.forum_hub.dto.topicos;

import com.edimilsonborges.forum_hub.models.Status;
import com.edimilsonborges.forum_hub.models.Topico;
import org.springframework.hateoas.Links;

import java.time.LocalDateTime;
import java.util.UUID;

public record DadosListagemTopicos(UUID id, String titulo, String mensagem, LocalDateTime dataCriacao, Status status, String autor, String curso, Links links) {
    public DadosListagemTopicos(Topico topico){
        this(topico.getId(),topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(),topico.getStatus(),topico.getUsuario().getNome(), topico.getCurso().getNome(), topico.getLinks());
    }
}
