package br.com.senac.ado.zoologico.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
public class AnimalDTO {
    private String nome;
    private String sexo;
    private LocalDate dataNascimento;
    private String status;
    private UUID especieId;
    private UUID habitatId;
    private Set<UUID> tratadoresIds;
}
