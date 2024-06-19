package com.edimilsonborges.forum_hub.repositories;

import com.edimilsonborges.forum_hub.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    UserDetails findByEmail(String username);
}
