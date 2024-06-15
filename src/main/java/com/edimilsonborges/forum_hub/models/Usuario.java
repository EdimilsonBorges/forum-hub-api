package com.edimilsonborges.forum_hub.models;

import com.edimilsonborges.forum_hub.dto.usuarios.DadosAtualizacaoUsuario;
import com.edimilsonborges.forum_hub.dto.usuarios.DadosCadastroUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Usuario extends RepresentationModel<Curso> implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nome;
    private String email;
    private String senha;
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topico> topicos;
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resposta> respostas;

    public Usuario(DadosCadastroUsuario dadosCadastroUsuario) {
        this.id = null;
        this.nome = dadosCadastroUsuario.nome();
        this.email = dadosCadastroUsuario.email();
        this.senha = criptografarSenha(dadosCadastroUsuario.senha());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public static Usuario getUsuarioLogado() {
        return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static boolean temPermisaoParaModificacao(Usuario usuario) {
        return Objects.equals(Usuario.getUsuarioLogado().getId(), usuario.getId());
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public void atualizarInformacoes(DadosAtualizacaoUsuario dadosAtualizacaoUsuario) {
        if (dadosAtualizacaoUsuario.nome() != null) {
            this.nome = dadosAtualizacaoUsuario.nome();
        }
        if (dadosAtualizacaoUsuario.email() != null) {
            this.email = dadosAtualizacaoUsuario.email();
        }
        if (dadosAtualizacaoUsuario.senha() != null) {
            this.senha = criptografarSenha(dadosAtualizacaoUsuario.senha());
        }
    }

    private String criptografarSenha(String senha){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(senha);
    }
}
