package br.com.senac.ado.zoologico.repository;

import br.com.senac.ado.zoologico.entity.CuidadorHabitat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CuidadorHabitatRepository extends JpaRepository<CuidadorHabitat, UUID> {}
