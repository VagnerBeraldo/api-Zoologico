// DTO corrigido (assegure-se de salvar este arquivo)
package br.com.senac.ado.zoologico.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AlimentacaoDTO {
    private UUID animalId;
    private UUID tratadorId;
    private LocalDate data;
    private String tipoRacao;
    private Double quantidadeKg;
    private LocalDateTime horario;
}
