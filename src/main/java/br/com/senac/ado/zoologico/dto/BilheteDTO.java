package br.com.senac.ado.zoologico.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BilheteDTO {
     String comprador;
     LocalDateTime dataCompra;
     Double valor;
     UUID eventoId;
}
