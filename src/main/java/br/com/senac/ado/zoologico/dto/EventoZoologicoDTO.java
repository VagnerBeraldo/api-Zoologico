package br.com.senac.ado.zoologico.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class EventoZoologicoDTO {
    private String titulo;
    private String descricao;
    private LocalDateTime data;
    private Integer capacidade;
    private Set<UUID> animaisIds;
}

