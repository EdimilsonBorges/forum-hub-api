package com.edimilsonborges.forum_hub.dto;

import com.edimilsonborges.forum_hub.models.Resposta;

public record DadosAtualizacaoResposta(String mensagem) {
    public DadosAtualizacaoResposta(Resposta resposta){
        this(resposta.getMensagem());
    }
}
