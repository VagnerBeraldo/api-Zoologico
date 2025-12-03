package br.com.senac.ado.zoologico.controller;

import br.com.senac.ado.zoologico.dto.Usuario.UsuarioDTO;
import br.com.senac.ado.zoologico.entity.Usuario;
import br.com.senac.ado.zoologico.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController implements GenericController{

    private static final String BASE_PATH = "/api/usuarios";

    private final UsuarioService service;

    @GetMapping
    public ResponseEntity<List<Usuario>> listAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }


    @PostMapping("/registrar")
    public ResponseEntity<Void> saveUser(@RequestBody @Valid UsuarioDTO request) {
        UUID idGerado = service.saveUser(request);
        URI location = gerarHeaderLocation(BASE_PATH,idGerado);
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/admin/registrar")
    public ResponseEntity<Void> saveAdminUser(@RequestBody @Valid UsuarioDTO request) {
        UUID idGerado = service.saveAdminUser(request);
        URI location = gerarHeaderLocation(BASE_PATH,idGerado);
        return ResponseEntity.created(location).build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody @Valid UsuarioDTO dto) {

        Usuario user = new Usuario();
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setSenha(dto.senha());

        service.update(id, user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
