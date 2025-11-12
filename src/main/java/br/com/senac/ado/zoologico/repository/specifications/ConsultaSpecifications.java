package br.com.senac.ado.zoologico.repository.specifications;

import br.com.senac.ado.zoologico.entity.ConsultaVeterinaria;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDate;

public class ConsultaSpecifications {

    public static Specification<ConsultaVeterinaria> buscarConsultasCompostas(
            String especialidadeVeterinario, boolean isUrgente, LocalDate dataMinima
    ){
        // Filtro 1: Especialidade do Veterinário
        Specification<ConsultaVeterinaria> filtroVeterinario = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("veterinario").get("especialidade"), especialidadeVeterinario
                );

        // Filtro 2: Urgência OU Data da Consulta (Condição OR)
        Specification<ConsultaVeterinaria> filtroUrgenciaOuData = (root, query, criteriaBuilder) ->
                criteriaBuilder.or(
                        // Condição 2a: É urgente
                        criteriaBuilder.equal(root.get("urgente"), isUrgente),
                        // Condição 2b: Data da consulta é após a data mínima
                        criteriaBuilder.greaterThanOrEqualTo(root.get("dataConsulta"), dataMinima)
                );

        // Combinação: (Filtro 1 AND Filtro 2)
        return filtroVeterinario.and(filtroUrgenciaOuData);
    }

}
