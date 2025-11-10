package br.com.senac.ado.zoologico.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ConsultaDTO {
    private LocalDate dataConsulta;
    private String diagnostico;
    private String tratamento;
    private String observacoes;
    private Boolean urgente;
    private UUID animalId;
    private UUID veterinarioId;
}
