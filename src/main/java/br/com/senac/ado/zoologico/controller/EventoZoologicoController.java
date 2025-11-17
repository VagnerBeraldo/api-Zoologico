package br.com.senac.ado.zoologico.controller;

import br.com.senac.ado.zoologico.dto.BilheteDTO;
import br.com.senac.ado.zoologico.dto.EventoZoologicoDTO;
import br.com.senac.ado.zoologico.entity.Bilhete;
import br.com.senac.ado.zoologico.entity.EventoZoologico;
import br.com.senac.ado.zoologico.service.EventoZoologicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoZoologicoController implements GenericController {

    private static final String BASE_PATH = "/api/eventos";

    private final EventoZoologicoService service;


    /**
     * Endpoint transacional para registrar a venda de um bilhete e
     * decrementar a capacidade do evento de forma atômica.
     * * @param dto O DTO contendo o ID do evento, nome do comprador e valor.
     *
     * @return Location para o Recurso Criado, com status HTTP 201 (Created).
     */
    @PostMapping("/vender-bilhete")
    public ResponseEntity<Bilhete> venderBilhete(@RequestBody @Valid BilheteDTO dto) {
        var idGerado = service.venderBilhete(dto);
        URI location = gerarHeaderLocation(BASE_PATH, idGerado);
        return ResponseEntity.created(location).build();
    }


    @GetMapping
    public List<EventoZoologico> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public EventoZoologico findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid EventoZoologicoDTO dto) {
        var idGerado = service.save(dto);
        URI location = gerarHeaderLocation(BASE_PATH, idGerado);
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id,
                                  @RequestBody @Valid EventoZoologicoDTO dto) {
         service.update(id, dto);
         return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
