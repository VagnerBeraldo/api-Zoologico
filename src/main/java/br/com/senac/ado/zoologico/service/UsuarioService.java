package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.dto.UsuarioDTO;
import br.com.senac.ado.zoologico.entity.Usuario;
import br.com.senac.ado.zoologico.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public List<Usuario> listarTodos() {
        return repo.findAll();
    }

    public Usuario buscar(UUID id) {
        return repo.findById(id).orElse(null);
    }

    public Usuario registrar(UsuarioDTO dto) {
        if (repo.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Usuário já existe");
        }

        Usuario user = new Usuario();
        user.setUsername(dto.getUsername());
        user.setSenha(dto.getSenha());
        user.setPapel(dto.getPapel());
        return repo.save(user);
    }

    public Usuario atualizar(UUID id, Usuario dto) {
        Usuario user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        user.setUsername(dto.getUsername());
        user.setSenha(dto.getSenha());
        user.setPapel(dto.getPapel());
        return repo.save(user);
    }

    public void excluir(UUID id) {
        repo.deleteById(id);
    }

    public boolean autenticar(String username, String senha) {
        Usuario user = repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return user.getSenha().equals(senha);
    }
}
