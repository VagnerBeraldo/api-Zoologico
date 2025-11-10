package br.com.senac.ado.zoologico.repository;

import br.com.senac.ado.zoologico.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface AnimalRepository extends JpaRepository<Animal, UUID> {
    List<Animal> findByEspecieNomeComum(String nomeComum);
    List<Animal> findByStatus(String status);
    List<Animal> findByEspecieNomeComumAndHabitatNomeAndStatus(String especie, String habitat, String status);
}
