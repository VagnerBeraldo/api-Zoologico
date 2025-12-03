package br.com.senac.ado.zoologico.repository;

import br.com.senac.ado.zoologico.entity.ConsultaVeterinaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ConsultaVeterinariaRepository extends JpaRepository<ConsultaVeterinaria, UUID> , JpaSpecificationExecutor<ConsultaVeterinaria> {
    List<ConsultaVeterinaria> findByVeterinarioId(UUID vetId);

    @Query("SELECT c.animal.nome AS nomeAnimal, " +
            "c.animal.especie.nomeComum AS especie, " +
            "c.veterinario.nome AS nomeVeterinario, " +
            "COUNT(c) AS totalConsultas " +
            "FROM ConsultaVeterinaria c " +
            "GROUP BY c.animal.nome, c.animal.especie.nomeComum, c.veterinario.nome " +
            "ORDER BY c.veterinario.nome, c.animal.nome")
    List<Map<String, Object>> contarConsultasProdutividade();
}
