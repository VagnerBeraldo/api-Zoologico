package br.com.senac.ado.zoologico.controller;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

public interface GenericController {

    //reutilizar nos controllers
    default URI gerarHeaderLocation(String basePath, UUID id) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()       // host + porta
                .path(basePath + "/{id}")       // caminho real do recurso
                .buildAndExpand(id)
                .toUri();
    }
}
