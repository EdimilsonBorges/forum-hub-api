package com.edimilsonborges.forum_hub.repositories;

import com.edimilsonborges.forum_hub.models.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface CursoRepisitory extends JpaRepository<Curso, UUID> {
    Curso findByNome(String curso);

}
