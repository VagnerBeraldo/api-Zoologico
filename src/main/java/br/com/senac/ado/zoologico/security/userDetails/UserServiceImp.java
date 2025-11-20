package br.com.senac.ado.zoologico.security.userDetails;

import br.com.senac.ado.zoologico.exception.ResourceNotFoundException;
import br.com.senac.ado.zoologico.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImp implements UserDetailsService {

    private final UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var usuario = repository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o email: " + username));
        return new UserDetailsImp(usuario);
    }
}
