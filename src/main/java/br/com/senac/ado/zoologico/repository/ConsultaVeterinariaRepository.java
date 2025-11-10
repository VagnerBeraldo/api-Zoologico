package br.com.senac.ado.zoologico.repository;

import br.com.senac.ado.zoologico.entity.ConsultaVeterinaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ConsultaVeterinariaRepository extends JpaRepository<ConsultaVeterinaria, UUID> {
    List<ConsultaVeterinaria> findByVeterinarioId(UUID vetId);

    @Query("SELECT c.animal.especie.nomeComum AS especie, COUNT(c) AS totalConsultas " +
            "FROM ConsultaVeterinaria c " +
            "GROUP BY c.animal.especie.nomeComum")
    List<Map<String, Object>> contarConsultasPorEspecie();
}
