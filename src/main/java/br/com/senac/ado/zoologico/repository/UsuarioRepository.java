package br.com.senac.ado.zoologico.repository;

import br.com.senac.ado.zoologico.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmailAndIdNot(String email, UUID id);
}
