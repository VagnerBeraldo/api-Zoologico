package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.dto.Usuario.UsuarioDTO;
import br.com.senac.ado.zoologico.entity.Usuario;
import br.com.senac.ado.zoologico.exception.ConflictException;
import br.com.senac.ado.zoologico.exception.ResourceNotFoundException;
import br.com.senac.ado.zoologico.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private br.com.senac.ado.zoologico.security.config.PasswordEncoderConfig passwordConfig;

    @InjectMocks
    private UsuarioService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        when(passwordConfig.bCryptPasswordEncoder()).thenReturn(new BCryptPasswordEncoder());
    }

    @Test
    void getAll_shouldReturnList() {
        List<Usuario> lista = List.of(new Usuario());
        when(repository.findAll()).thenReturn(lista);

        List<Usuario> result = service.getAll();

        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findById_shouldReturnUser() {
        UUID id = UUID.randomUUID();
        Usuario u = new Usuario();
        u.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(u));

        Usuario result = service.findById(id);

        assertEquals(id, result.getId());
    }

    @Test
    void findById_shouldThrowNotFound() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(id));
    }

    @Test
    void saveUser_shouldCreateUser() {
        UsuarioDTO dto = new UsuarioDTO("joao", "j@a.com", "1234");

        Usuario saved = new Usuario();
        saved.setId(UUID.randomUUID());

        when(repository.findByEmail(dto.email())).thenReturn(Optional.empty());
        when(repository.save(any())).thenReturn(saved);

        UUID id = service.saveUser(dto);

        assertNotNull(id);
        verify(repository, times(1)).save(any());
    }

    @Test
    void saveUser_shouldThrowConflict() {
        UsuarioDTO dto = new UsuarioDTO("joao", "j@a.com", "1234");

        when(repository.findByEmail(dto.email())).thenReturn(Optional.of(new Usuario()));

        assertThrows(ConflictException.class, () -> service.saveUser(dto));
    }

    @Test
    void update_shouldUpdateUser() {
        UUID id = UUID.randomUUID();

        Usuario existing = new Usuario();
        existing.setId(id);

        Usuario dto = new Usuario();
        dto.setUsername("novo");
        dto.setEmail("novo@email.com");
        dto.setSenha("senha");

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.existsByEmailAndIdNot(dto.getEmail(), id)).thenReturn(false);

        service.update(id, dto);

        verify(repository, times(1)).save(existing);
        assertEquals("novo", existing.getUsername());
    }

    @Test
    void update_shouldThrowConflict() {
        UUID id = UUID.randomUUID();

        Usuario existing = new Usuario();
        existing.setId(id);

        Usuario dto = new Usuario();
        dto.setEmail("email@ex.com");

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.existsByEmailAndIdNot(dto.getEmail(), id)).thenReturn(true);

        assertThrows(ConflictException.class, () -> service.update(id, dto));
    }

    @Test
    void delete_shouldDelete() {
        UUID id = UUID.randomUUID();

        when(repository.existsById(id)).thenReturn(true);

        service.deleteById(id);

        verify(repository).deleteById(id);
    }

    @Test
    void delete_shouldThrowNotFound() {
        UUID id = UUID.randomUUID();
        when(repository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.deleteById(id));
    }
}
