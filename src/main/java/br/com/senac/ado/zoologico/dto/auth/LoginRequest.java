package br.com.senac.ado.zoologico.dto.auth;

public record LoginRequest(
        String email,
        String password
) {
}
