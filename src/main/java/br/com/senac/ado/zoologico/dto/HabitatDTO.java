package br.com.senac.ado.zoologico.dto;

import lombok.Data;

@Data
public class HabitatDTO {
    private String nome;
    private String tipo;
    private String areaM2;
    private String descricao;
}
