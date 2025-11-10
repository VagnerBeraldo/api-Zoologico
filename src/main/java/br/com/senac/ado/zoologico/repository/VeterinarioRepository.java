package br.com.senac.ado.zoologico.repository;

import br.com.senac.ado.zoologico.entity.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VeterinarioRepository extends JpaRepository<Veterinario, UUID> {}
