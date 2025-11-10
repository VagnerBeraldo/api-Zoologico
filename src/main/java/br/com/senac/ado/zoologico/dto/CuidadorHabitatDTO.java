package br.com.senac.ado.zoologico.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CuidadorHabitatDTO {
    private UUID id;
    private UUID tratadorId;
    private UUID habitatId;
    private String turno;
}

