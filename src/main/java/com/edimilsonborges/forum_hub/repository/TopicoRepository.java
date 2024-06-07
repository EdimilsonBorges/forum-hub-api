package com.edimilsonborges.forum_hub.repository;

import com.edimilsonborges.forum_hub.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
}
