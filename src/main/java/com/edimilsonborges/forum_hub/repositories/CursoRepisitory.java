package com.edimilsonborges.forum_hub.repositories;

import com.edimilsonborges.forum_hub.models.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepisitory extends JpaRepository<Curso, Long> {
    Curso findByNome(String curso);
}
