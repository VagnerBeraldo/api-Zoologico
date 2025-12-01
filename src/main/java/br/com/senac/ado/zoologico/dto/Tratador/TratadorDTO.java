package br.com.senac.ado.zoologico.dto.Tratador;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

public record TratadorDTO(
        @NotBlank String nome,
        @NotBlank @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos numéricos") @CPF String cpf,
        @NotBlank String especialidade,
        @NotBlank String telefone
) {}
