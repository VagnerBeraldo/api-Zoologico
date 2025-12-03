package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.dto.Usuario.UsuarioDTO;
import br.com.senac.ado.zoologico.entity.Usuario;
import br.com.senac.ado.zoologico.enums.Roles;
import br.com.senac.ado.zoologico.exception.ConflictException;
import br.com.senac.ado.zoologico.exception.ResourceNotFoundException;
import br.com.senac.ado.zoologico.repository.UsuarioRepository;
import br.com.senac.ado.zoologico.security.config.PasswordEncoderConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private PasswordEncoderConfig passwordEncoderConfig;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UsuarioService service;

    private Usuario usuario;
    private UUID id;

    @BeforeEach
    void setup() {
        id = UUID.randomUUID();
        usuario = new Usuario();
        usuario.setId(id);
        usuario.setUsername("Erick");
        usuario.setEmail("erick@test.com");
        usuario.setSenha("123");
        usuario.setRole(Roles.USER);
    }

    // ---------- GET ALL ----------
    @Test
    void getAll_shouldReturnList() {
        when(repository.findAll()).thenReturn(List.of(usuario));

        List<Usuario> result = service.getAll();

        assertEquals(1, result.size());
        assertEquals("Erick", result.get(0).getUsername());
    }

    // ---------- FIND BY ID ----------
    @Test
    void findById_shouldReturnUser() {
        when(repository.findById(id)).thenReturn(Optional.of(usuario));

        Usuario result = service.findById(id);

        assertEquals("Erick", result.getUsername());
    }

    @Test
    void findById_shouldThrowNotFound() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(id));
    }

    // ---------- SAVE USER ----------
    @Test
    void saveUser_shouldCreateUser() {
        UsuarioDTO dto = new UsuarioDTO("Erick", "erick@test.com", "123");

        when(repository.findByEmail(dto.email())).thenReturn(Optional.empty());
        when(passwordEncoderConfig.bCryptPasswordEncoder()).thenReturn((BCryptPasswordEncoder) encoder);
        when(encoder.encode(dto.senha())).thenReturn("hashed");
        when(repository.save(any())).thenReturn(usuario);

        UUID result = service.saveUser(dto);

        assertEquals(id, result);
        verify(repository).save(any(Usuario.class));
    }

    @Test
    void saveUser_shouldThrowConflict() {
        UsuarioDTO dto = new UsuarioDTO("Erick", "erick@test.com", "123");

        when(repository.findByEmail(dto.email())).thenReturn(Optional.of(usuario));

        assertThrows(ConflictException.class, () -> service.saveUser(dto));
    }

    // ----------- UPDATE -----------
    @Test
    void update_shouldModifyUser() {
        Usuario novo = new Usuario();
        novo.setUsername("Novo");
        novo.setEmail("novo@test.com");
        novo.setSenha("999");

        when(repository.findById(id)).thenReturn(Optional.of(usuario));
        when(repository.existsByEmailAndIdNot(novo.getEmail(), id)).thenReturn(false);

        service.update(id, novo);

        verify(repository).save(usuario);
        assertEquals("Novo", usuario.getUsername());
    }

    @Test
    void update_shouldThrowConflictForEmail() {
        Usuario novo = new Usuario();
        novo.setEmail("email@existe.com");

        when(repository.findById(id)).thenReturn(Optional.of(usuario));
        when(repository.existsByEmailAndIdNot(novo.getEmail(), id)).thenReturn(true);

        assertThrows(ConflictException.class, () -> service.update(id, novo));
    }

    @Test
    void update_shouldThrowNotFound() {
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.update(id, usuario));
    }

    // ----------- DELETE -----------
    @Test
    void deleteById_shouldDelete() {
        when(repository.existsById(id)).thenReturn(true);

        service.deleteById(id);

        verify(repository).deleteById(id);
    }

    @Test
    void deleteById_shouldThrowNotFound() {
        when(repository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.deleteById(id));
    }
}

