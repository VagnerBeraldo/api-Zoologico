package br.com.senac.ado.zoologico.controller;

import br.com.senac.ado.zoologico.dto.TratadorDTO;
import br.com.senac.ado.zoologico.entity.Tratador;
import br.com.senac.ado.zoologico.service.TratadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tratadores")
@RequiredArgsConstructor
public class TratadorController implements GenericController {

    private static final String BASE_PATH = "/api/tratadores";

    private final TratadorService service;

    @GetMapping
    public ResponseEntity<List<Tratador>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tratador> findById(@PathVariable UUID id) {
         return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody TratadorDTO dto) {

        UUID idGerado = service.save(dto);
        URI location = gerarHeaderLocation(BASE_PATH,idGerado);

        return  ResponseEntity.created(location).build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody TratadorDTO dto) {
        Tratador tratador = new Tratador();
        tratador.setNome(dto.getNome());
        tratador.setCpf(dto.getCpf());
        tratador.setTelefone(dto.getTelefone());

        service.update(id, tratador);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
