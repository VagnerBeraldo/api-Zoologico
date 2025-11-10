package br.com.senac.ado.zoologico.repository;

import br.com.senac.ado.zoologico.entity.Tratador;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TratadorRepository extends JpaRepository<Tratador, UUID> {}
