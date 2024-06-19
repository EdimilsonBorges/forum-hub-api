package com.edimilsonborges.forum_hub.dto.usuarios;

import jakarta.validation.constraints.Email;

public record DadosAtualizacaoUsuario(String nome, @Email String email, String senha) {
}
