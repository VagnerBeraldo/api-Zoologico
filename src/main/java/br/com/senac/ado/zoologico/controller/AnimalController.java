package br.com.senac.ado.zoologico.controller;

import br.com.senac.ado.zoologico.dto.Animal.AnimalDTO;
import br.com.senac.ado.zoologico.entity.Animal;
import br.com.senac.ado.zoologico.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/animais")
@RequiredArgsConstructor
public class AnimalController implements GenericController {


    private static final String BASE_PATH = "/api/animais";
    private final AnimalService service;


    @GetMapping
    public List<Animal> findByFilters(
            @RequestParam(required = false) String especie,
            @RequestParam(required = false) String habitat,
            @RequestParam(required = false) String status)
    {
        return service.findByFilters(especie, habitat, status);
    }

    @GetMapping("/contagem-por-especie")
    public Map<String, Long> contagemPorEspecie() {
        return service.contarPorEspecie();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Animal> findById(@PathVariable UUID id) {
          return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody AnimalDTO dto) {
        var idGerado = service.save(dto);
        URI location = gerarHeaderLocation(BASE_PATH, idGerado);
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody AnimalDTO dto) {
         service.update(id, dto);
         return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

//
//    @GetMapping("/estatisticas/por-especie")
//    public Map<String, Long> contarPorEspecie() {
//        return service.contarPorEspecie();
//    }


}
