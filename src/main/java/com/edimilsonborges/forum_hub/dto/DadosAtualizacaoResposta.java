package com.edimilsonborges.forum_hub.dto;

import com.edimilsonborges.forum_hub.models.Resposta;
import jakarta.validation.constraints.NotBlank;

public record DadosAtualizacaoResposta(@NotBlank String mensagem) {
    public DadosAtualizacaoResposta(Resposta resposta){
        this(resposta.getMensagem());
    }
}
