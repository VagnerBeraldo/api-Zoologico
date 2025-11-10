package br.com.senac.ado.zoologico.controller;

import br.com.senac.ado.zoologico.dto.UsuarioDTO;
import br.com.senac.ado.zoologico.entity.Usuario;
import br.com.senac.ado.zoologico.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @GetMapping
    public List<Usuario> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Usuario buscarPorId(@PathVariable UUID id) {
        return service.buscar(id);
    }

    @PostMapping("/registrar")
    public Map<String, String> registrar(@RequestBody UsuarioDTO dto) {
        service.registrar(dto);
        return Map.of("mensagem", "Usuário registrado com sucesso!");
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody UsuarioDTO dto) {
        boolean ok = service.autenticar(dto.getUsername(), dto.getSenha());
        return Map.of("mensagem", ok ? "Login realizado com sucesso!" : "Usuário ou senha incorretos!");
    }

    @PutMapping("/{id}")
    public Usuario atualizar(@PathVariable UUID id, @RequestBody UsuarioDTO dto) {
        Usuario user = new Usuario();
        user.setUsername(dto.getUsername());
        user.setSenha(dto.getSenha());
        user.setPapel(dto.getPapel());
        return service.atualizar(id, user);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable UUID id) {
        service.excluir(id);
    }
}
