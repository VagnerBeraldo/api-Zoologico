package br.com.senac.ado.zoologico.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private String username;
    private String email;
    private String senha;
}
