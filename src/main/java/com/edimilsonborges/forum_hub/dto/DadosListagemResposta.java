package com.edimilsonborges.forum_hub.dto;

import com.edimilsonborges.forum_hub.models.Resposta;

import java.time.LocalDateTime;

public record DadosListagemResposta(Long id, String autor, String mensagem , String topico, boolean solucao,
                                    LocalDateTime dataCriacao) {
    public DadosListagemResposta(Resposta resposta) {
        this(
                resposta.getId(),
                resposta.getUsuario().getNome(),
                resposta.getMensagem(),
                resposta.getTopico().getTitulo(),
                resposta.getSolucao(),
                resposta.getDataCriacao());
    }
}
