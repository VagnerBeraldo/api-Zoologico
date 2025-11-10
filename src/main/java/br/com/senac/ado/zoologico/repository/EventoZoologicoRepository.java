package br.com.senac.ado.zoologico.repository;

import br.com.senac.ado.zoologico.entity.EventoZoologico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventoZoologicoRepository extends JpaRepository<EventoZoologico, UUID> {}
