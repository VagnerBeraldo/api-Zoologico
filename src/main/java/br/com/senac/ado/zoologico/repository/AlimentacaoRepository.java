package br.com.senac.ado.zoologico.repository;

import br.com.senac.ado.zoologico.entity.Alimentacao;
import br.com.senac.ado.zoologico.entity.AlimentacaoId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AlimentacaoRepository extends JpaRepository<Alimentacao, AlimentacaoId> {
    List<Alimentacao> findByAnimalId(UUID animalId);

}
