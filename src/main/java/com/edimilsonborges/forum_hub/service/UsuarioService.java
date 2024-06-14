package com.edimilsonborges.forum_hub.service;

import com.edimilsonborges.forum_hub.dto.status.DadosErros;
import com.edimilsonborges.forum_hub.dto.status.DadosSucesso;
import com.edimilsonborges.forum_hub.dto.usuarios.DadosAtualizacaoUsuario;
import com.edimilsonborges.forum_hub.dto.usuarios.DadosCadastroUsuario;
import com.edimilsonborges.forum_hub.dto.usuarios.DadosListagemUsuarios;
import com.edimilsonborges.forum_hub.models.Usuario;
import com.edimilsonborges.forum_hub.repositories.UsuarioRepository;
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
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public ResponseEntity<?> cadastrarUsuario(DadosCadastroUsuario dadosCadastroUsuario, UriComponentsBuilder uriBuilder) {
        Usuario usuario = usuarioRepository.save(new Usuario(dadosCadastroUsuario));
        URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosListagemUsuarios(usuario));
    }

    public ResponseEntity<Page<DadosListagemUsuarios>> listarTodosUsuarios(Pageable paginacao){
       Page<DadosListagemUsuarios> page = usuarioRepository.findAll(paginacao)
               .map(usuario -> new DadosListagemUsuarios(usuario.getId(),usuario.getNome()));

        return ResponseEntity.ok(page);
    }

    public ResponseEntity<?> listarUsuario(Long id) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            return ResponseEntity.ok(new DadosListagemUsuarios(usuario.getId(), usuario.getNome()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DadosErros("Usuário não encontrado!"));
    }

    public ResponseEntity<?> atualizarUsuario(DadosAtualizacaoUsuario dadosAtualizacaoUsuario, Long id) {

        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DadosErros("Usuário não existe!"));
        }

        Usuario usuario = optionalUsuario.get();
        if (!Usuario.temPermisaoParaModificacao(usuario)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new DadosErros("Você não tem permissão para modificar a conta de outra pessoa!"));
        }

        usuario.atualizarInformacoes(dadosAtualizacaoUsuario);
        return ResponseEntity.ok(new DadosListagemUsuarios(usuario));
    }

    public ResponseEntity<?> cancelarConta(Long id) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DadosErros("Conta não encontrado para o cancelamento!"));
        }

        Usuario usuario = optionalUsuario.get();
        if (!Usuario.temPermisaoParaModificacao(usuario)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new DadosErros("Você não tem permissão para cancelar a conta de outra pessoa!"));
        }

        usuarioRepository.deleteById(id);
        return ResponseEntity.ok(new DadosSucesso("Conta cancelada com sucesso!"));
    }
}
