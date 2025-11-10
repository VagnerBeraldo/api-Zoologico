package br.com.senac.ado.zoologico.repository;

import br.com.senac.ado.zoologico.entity.Alimentacao;
import br.com.senac.ado.zoologico.entity.AlimentacaoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlimentacaoRepository extends JpaRepository<Alimentacao, AlimentacaoId> {

}
