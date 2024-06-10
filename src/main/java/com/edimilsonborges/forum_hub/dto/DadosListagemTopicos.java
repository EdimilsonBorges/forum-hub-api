package com.edimilsonborges.forum_hub.dto;

import com.edimilsonborges.forum_hub.model.Status;
import com.edimilsonborges.forum_hub.model.Topico;

import java.time.LocalDateTime;

public record DadosListagemTopicos(Long id, String titulo, String mensagem, LocalDateTime dataCriacao, Status status, String autor, String curso) {
    public DadosListagemTopicos(Topico topico){
        this(topico.getId(),topico.getTitulo(), topico.getMensagem(), topico.getDataCriacao(),topico.getStatus(),topico.getUsuario().getNome(), topico.getCurso().getNome());
    }
}