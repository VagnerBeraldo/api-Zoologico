package br.com.senac.ado.zoologico.dto;

import lombok.Data;

@Data
public class EspecieDTO {
    private String nomeComum;
    private String nomeCientifico;
    private String statusConservacao;
}
