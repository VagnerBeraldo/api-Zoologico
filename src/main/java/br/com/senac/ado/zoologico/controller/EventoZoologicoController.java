package br.com.senac.ado.zoologico.controller;

import br.com.senac.ado.zoologico.dto.BilheteDTO;
import br.com.senac.ado.zoologico.dto.EventoZoologicoDTO;
import br.com.senac.ado.zoologico.entity.Bilhete;
import br.com.senac.ado.zoologico.entity.EventoZoologico;
import br.com.senac.ado.zoologico.service.EventoZoologicoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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


    /**
     * Endpoint transacional para registrar a venda de um bilhete e
     * decrementar a capacidade do evento de forma atômica.
     * * @param dto O DTO contendo o ID do evento, nome do comprador e valor.
     * @return O Bilhete recém-criado, com status HTTP 201 (Created).
     */
    @PostMapping("/vender-bilhete")
    public ResponseEntity<Bilhete> venderBilhete(@RequestBody BilheteDTO dto) {
        try {
            Bilhete bilheteCriado = service.venderBilhete(dto);

            return new ResponseEntity<>(bilheteCriado, HttpStatus.CREATED);

        } catch (EntityNotFoundException e) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);

        } catch (IllegalStateException e) {

            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);

        } catch (Exception e) {

            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Falha ao processar a venda do bilhete. A transação foi desfeita.",
                    e
            );
        }
    }
}
