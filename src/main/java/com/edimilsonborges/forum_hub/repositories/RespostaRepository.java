package com.edimilsonborges.forum_hub.repositories;

import com.edimilsonborges.forum_hub.models.Resposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RespostaRepository extends JpaRepository<Resposta, Long> {
}
