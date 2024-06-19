package com.edimilsonborges.forum_hub.dto.usuarios;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record DadosAtualizacaoUsuario(String nome, @Email String email,  @Size(min = 8, max = 15) String senha) {
}
