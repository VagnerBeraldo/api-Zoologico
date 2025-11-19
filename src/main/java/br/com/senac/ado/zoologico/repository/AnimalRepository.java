package br.com.senac.ado.zoologico.repository;

import br.com.senac.ado.zoologico.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AnimalRepository extends JpaRepository<Animal, UUID> {


    @Query("""
    SELECT a FROM Animal a
    WHERE (:especie IS NULL OR a.especie.nomeComum = :especie)
      AND (:habitat IS NULL OR a.habitat.nome = :habitat)
      AND (:status IS NULL OR a.status = :status)
    """)
    List<Animal> findByFilters(String especie, String habitat, String status);

    List<Animal> findByEspecieNomeComum(String nomeComum);
    List<Animal> findByStatus(String status);
    List<Animal> findByEspecieNomeComumAndHabitatNomeAndStatus(String especie, String habitat, String status);
}
