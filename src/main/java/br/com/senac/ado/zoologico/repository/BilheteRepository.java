package br.com.senac.ado.zoologico.repository;

import br.com.senac.ado.zoologico.entity.Bilhete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface BilheteRepository extends JpaRepository<Bilhete, UUID> {


    @Query("SELECT COUNT(b) FROM Bilhete b WHERE b.evento.id = :eventoId")
    long countByEventoId(@Param("eventoId") UUID eventoId);

    @Query("SELECT b.evento.titulo AS titulo, COUNT(b) AS total FROM Bilhete b GROUP BY b.evento.titulo")
    List<Object[]> contarPorEvento();
}
