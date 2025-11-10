package br.com.senac.ado.zoologico.dto;

import lombok.Data;

@Data
public class VeterinarioDTO {
    private String nome;
    private String especialidade;
    private String crmv;
}
