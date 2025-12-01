package br.com.senac.ado.zoologico.dto.Usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public record UsuarioDTO(
        @NotBlank String username,
        @NotBlank @Email String email,
        @NotBlank String senha
) {}