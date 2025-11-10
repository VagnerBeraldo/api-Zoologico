package br.com.senac.ado.zoologico.controller;

import br.com.senac.ado.zoologico.dto.EventoZoologicoDTO;
import br.com.senac.ado.zoologico.entity.EventoZoologico;
import br.com.senac.ado.zoologico.service.EventoZoologicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoZoologicoController {

    private final EventoZoologicoService service;


    @GetMapping
    public List<EventoZoologico> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public EventoZoologico buscarPorId(@PathVariable UUID id) {
        return service.buscar(id);
    }

    @PostMapping
    public EventoZoologico criar(@RequestBody EventoZoologicoDTO dto) {
        EventoZoologico evento = new EventoZoologico();
        evento.setTitulo(dto.getTitulo());
        evento.setDescricao(dto.getDescricao());
        evento.setData(LocalDate.from(dto.getData()));
        evento.setCapacidade(dto.getCapacidade());
        return service.salvar(evento);
    }

    @PutMapping("/{id}")
    public EventoZoologico atualizar(@PathVariable UUID id, @RequestBody EventoZoologicoDTO dto) {
        EventoZoologico evento = new EventoZoologico();
        evento.setTitulo(dto.getTitulo());
        evento.setDescricao(dto.getDescricao());
        evento.setData(LocalDate.from(dto.getData()));
        evento.setCapacidade(dto.getCapacidade());
        return service.atualizar(id, evento);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable UUID id) {
        service.excluir(id);
    }
}
