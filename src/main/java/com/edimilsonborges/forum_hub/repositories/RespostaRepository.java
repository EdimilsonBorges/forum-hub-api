package com.edimilsonborges.forum_hub.repositories;

import com.edimilsonborges.forum_hub.models.Resposta;
import com.edimilsonborges.forum_hub.models.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RespostaRepository extends JpaRepository<Resposta, Long> {
    @Query("SELECT res FROM Resposta res WHERE :solucao = true AND res.topico = :topico")
    List<Resposta> buscarRespostaPorSolucaoPorTopico(boolean solucao, Topico topico);
}
