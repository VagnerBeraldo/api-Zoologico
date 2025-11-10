package br.com.senac.ado.zoologico.repository;

import br.com.senac.ado.zoologico.entity.Especie;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EspecieRepository extends JpaRepository<Especie, UUID> {}
