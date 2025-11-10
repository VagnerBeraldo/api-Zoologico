package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.dto.UsuarioDTO;
import br.com.senac.ado.zoologico.entity.Usuario;
import br.com.senac.ado.zoologico.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;

    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    public Usuario buscar(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public Usuario registrar(UsuarioDTO dto) {
        if (repository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Usuário já existe");
        }

        Usuario user = new Usuario();
        user.setUsername(dto.getUsername());
        user.setSenha(dto.getSenha());
        user.setPapel(dto.getPapel());
        return repository.save(user);
    }

    public Usuario atualizar(UUID id, Usuario dto) {
        Usuario user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        user.setUsername(dto.getUsername());
        user.setSenha(dto.getSenha());
        user.setPapel(dto.getPapel());
        return repository.save(user);
    }

    public void excluir(UUID id) {
        repository.deleteById(id);
    }

    public boolean autenticar(String username, String senha) {
        Usuario user = repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return user.getSenha().equals(senha);
    }
}
