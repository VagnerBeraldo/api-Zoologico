package br.com.senac.ado.zoologico.controller;

import br.com.senac.ado.zoologico.dto.UsuarioDTO;
import br.com.senac.ado.zoologico.entity.Usuario;
import br.com.senac.ado.zoologico.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

//    @PostMapping("/login")
//    public Map<String, String> login(@RequestBody UsuarioDTO dto) {
//        boolean ok = service.autenticar(dto.getEmail(), dto.getSenha());
//        return Map.of("mensagem", ok ? "Login realizado com sucesso!" : "Usuário ou senha incorretos!");
//    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody @Valid UsuarioDTO dto) {

        Usuario user = new Usuario();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setSenha(dto.getSenha());

        service.update(id, user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
