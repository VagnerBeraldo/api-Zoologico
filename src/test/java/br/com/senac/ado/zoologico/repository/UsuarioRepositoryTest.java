package br.com.senac.ado.zoologico.repository;

import br.com.senac.ado.zoologico.entity.Usuario;
import br.com.senac.ado.zoologico.enums.Roles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository repository;

    @Test
    void salvarEEncontrarPorId() {
        Usuario user = new Usuario();
        user.setUsername("teste");
        user.setEmail("teste@mail.com");
        user.setSenha("123");
        user.setRole(Roles.USER);

        Usuario saved = repository.save(user);

        assertTrue(repository.findById(saved.getId()).isPresent());
    }

    @Test
    void existsByEmailAndIdNot_funciona() {
        Usuario u1 = new Usuario();
        u1.setUsername("A");
        u1.setEmail("a@mail.com");
        u1.setSenha("1");
        u1.setRole(Roles.USER);
        repository.save(u1);

        boolean exists = repository.existsByEmailAndIdNot("a@mail.com", UUID.randomUUID());

        assertTrue(exists);
    }
}
