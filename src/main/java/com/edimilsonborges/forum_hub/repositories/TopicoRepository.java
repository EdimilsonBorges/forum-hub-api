package com.edimilsonborges.forum_hub.repositories;

import com.edimilsonborges.forum_hub.models.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, UUID> {
}
