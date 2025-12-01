package br.com.senac.ado.zoologico.controller;

import br.com.senac.ado.zoologico.dto.Consulta.ConsultaDTO;
import br.com.senac.ado.zoologico.entity.ConsultaVeterinaria;
import br.com.senac.ado.zoologico.service.ConsultaVeterinariaService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/consultas")
@RequiredArgsConstructor
public class ConsultaVeterinariaController implements GenericController {

    private static final String BASE_PATH = "/api/consultas";
    private final ConsultaVeterinariaService service;

    @GetMapping
    public ResponseEntity<List<ConsultaVeterinaria>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ConsultaVeterinaria findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody ConsultaDTO dto) {
        var idGerado = service.save(dto);
        URI location = gerarHeaderLocation(BASE_PATH, idGerado);
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody ConsultaDTO dto) {
        service.update(id,dto);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public void excluir(@PathVariable UUID id) {
        service.excluir(id);
    }

    /**
     * Endpoint para buscar consultas com múltiplos critérios e filtros compostos.
     * Combina: (Especialidade DO VETERINÁRIO AND (É URGENTE OR Data >= Data Mínima))
     * * @param especialidade A especialidade do veterinário (ex: "Cirurgia"). OBRIGATÓRIO.
     * @param urgente Se a consulta foi marcada como urgente (true/false). OBRIGATÓRIO.
     * @param dataMin Data mínima para a consulta, no formato YYYY-MM-DD. OBRIGATÓRIO.
     * @return Lista de ConsultasVeterinarias que atendem aos critérios.
     */
    @GetMapping("/busca-composta")
    public ResponseEntity<List<ConsultaVeterinaria>> buscarPorFiltros(
            @RequestParam("especialidade") String especialidade,
            @RequestParam("urgente") Boolean urgente,
            @RequestParam("dataMin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataMin) {

        List<ConsultaVeterinaria> consultas =
                service.buscarPorFiltros(especialidade, urgente, dataMin);

        if (consultas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(consultas);
    }


}
