package br.com.senac.ado.zoologico.controller;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public interface GenericController {

    //reutilizar nos controllers
    default URI gerarHeaderLocation(String basePath, Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath() // inclui host, porta e contexto
                .path(basePath + "/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}
