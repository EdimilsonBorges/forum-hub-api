package com.edimilsonborges.forum_hub.dto.topicos;

import com.edimilsonborges.forum_hub.models.Status;
import com.edimilsonborges.forum_hub.models.Topico;

import java.time.LocalDateTime;

public record DadosListagemTopicos(Long id, String titulo, String mensagem, LocalDateTime dataCriacao, Status status, String autor, String curso) {
    public DadosListagemTopicos(Topico topico){
        this(topico.getId(),topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(),topico.getStatus(),topico.getUsuario().getNome(), topico.getCurso().getNome());
    }
}
