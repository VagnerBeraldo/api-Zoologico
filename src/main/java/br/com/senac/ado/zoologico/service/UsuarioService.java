package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.dto.Usuario.UsuarioDTO;
import br.com.senac.ado.zoologico.entity.Usuario;
import br.com.senac.ado.zoologico.enums.Roles;
import br.com.senac.ado.zoologico.exception.ConflictException;
import br.com.senac.ado.zoologico.exception.ResourceNotFoundException;
import br.com.senac.ado.zoologico.repository.UsuarioRepository;
import br.com.senac.ado.zoologico.security.config.PasswordEncoderConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoderConfig passwordEncoderConfig;

    public List<Usuario> getAll() {
        return repository.findAll();
    }

    public Usuario findById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recurso não Encontrado com o ID informado"));
    }

    public UUID saveUser(UsuarioDTO dto) {

       repository.findByEmail(dto.email()).ifPresent(user -> {
          throw new ConflictException("Usuario já cadastrado");
       });

        Usuario user = new Usuario();
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        String senhaCriptografada = passwordEncoderConfig.bCryptPasswordEncoder().encode(dto.senha());
        user.setSenha(senhaCriptografada);
        user.setRole(Roles.USER);
        return repository.save(user).getId();
    }


    public UUID saveAdminUser(UsuarioDTO dto) {

        repository.findByEmail(dto.email()).ifPresent(user -> {
            throw new ConflictException("Usuario já cadastrado");
        });

        Usuario user = new Usuario();
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        String senhaCriptografada = passwordEncoderConfig.bCryptPasswordEncoder().encode(dto.senha());
        user.setSenha(senhaCriptografada);
        user.setRole(Roles.ADMIN);
        return repository.save(user).getId();
    }

    public void update(UUID id, Usuario dto) {


        Usuario user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não Encontrado com o ID informado"));

        if (repository.existsByEmailAndIdNot(dto.getEmail(), id)) {
            throw new ConflictException("Conflito de dados , tente novamente");
        }

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setSenha(dto.getSenha());
        repository.save(user);
    }

    public void deleteById(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado para exclusão");
        }
        repository.deleteById(id);
    }

//    public boolean autenticar(String username, String senha) {
//        Usuario user = repository.findByEmail(username)
//                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
//        return user.getSenha().equals(senha);
//    }
}
