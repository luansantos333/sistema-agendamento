package com.sistemaagendamento.repository;

import com.sistemaagendamento.entities.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AgendamentoRepository extends JpaRepository <Agendamento, Long> {

@Query ("""

SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END 
FROM Agendamento a WHERE a.usuario = :usuario AND 
a.status = com.sistemaagendamento.entities.Status.AGENDADO
AND (a.dataInicio < :fim AND a.dataFim > :inicio) 
AND (:ignoreId IS NULL OR a.id <> :ignoreId) 


""")
boolean existsConflito(@Param("usuario") String usuario,
                       @Param("inicio")LocalDateTime inicio,
                       @Param("fim") LocalDateTime fim,
                       @Param ("ignoreId") Long ignoreId);




}
