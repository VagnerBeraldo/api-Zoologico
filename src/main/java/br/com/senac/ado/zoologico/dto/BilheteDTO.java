package br.com.senac.ado.zoologico.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BilheteDTO {
    private String comprador;
    private LocalDateTime dataCompra;
    private Double valor;
    private UUID eventoId;
}
