package br.com.senac.ado.zoologico.controller;

import br.com.senac.ado.zoologico.dto.BilheteDTO;
import br.com.senac.ado.zoologico.entity.Bilhete;
import br.com.senac.ado.zoologico.service.BilheteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/bilhetes")
@RequiredArgsConstructor
public class BilheteController {

    private final BilheteService service;


    @GetMapping
    public List<Bilhete> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Bilhete buscar(@PathVariable UUID id) {
        return service.buscar(id);
    }

    @PostMapping
    public Bilhete criar(@RequestBody BilheteDTO dto) {
        return service.comprar(dto);
    }

    @PutMapping("/{id}")
    public Bilhete atualizar(@PathVariable UUID id, @RequestBody BilheteDTO dto) {
        return service.atualizar(id, dto);
    }


    @DeleteMapping("/{id}")
    public void excluir(@PathVariable UUID id) {
        service.excluir(id);
    }

    @GetMapping("/estatisticas/total-por-evento")
    public Map<String, Long> totalPorEvento() {
        return service.totalBilhetesPorEvento();
    }



}
