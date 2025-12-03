package br.com.senac.ado.zoologico.controller;

import br.com.senac.ado.zoologico.service.ConsultaRelatorioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/relatorios")
public class ConsultaRelatorioController {

    private final ConsultaRelatorioService service;

    public ConsultaRelatorioController(ConsultaRelatorioService service) {
        this.service = service;
    }

    @GetMapping("/consultas-por-especie")
    public ResponseEntity<List<Map<String, Object>>> consultasProtutividade() {
        return ResponseEntity.ok(service.consultasProdutividade());
    }
}
