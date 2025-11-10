package br.com.senac.ado.zoologico.repository;

import br.com.senac.ado.zoologico.entity.Bilhete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BilheteRepository extends JpaRepository<Bilhete, UUID> {}
