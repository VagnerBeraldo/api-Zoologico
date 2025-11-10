package br.com.senac.ado.zoologico.repository;

import br.com.senac.ado.zoologico.entity.Habitat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HabitatRepository extends JpaRepository<Habitat, UUID> {}
