package com.edimilsonborges.forum_hub.repository;

import com.edimilsonborges.forum_hub.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepisitory extends JpaRepository<Curso, Long> {
    Curso findByNome(String curso);
}
