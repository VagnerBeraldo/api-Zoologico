package br.com.senac.ado.zoologico.service;

import br.com.senac.ado.zoologico.dto.Usuario.UsuarioDTO;
import br.com.senac.ado.zoologico.entity.Usuario;
import br.com.senac.ado.zoologico.enums.Roles;
import br.com.senac.ado.zoologico.exception.ConflictException;
import br.com.senac.ado.zoologico.exception.ResourceNotFoundException;
import br.com.senac.ado.zoologico.repository.UsuarioRepository;
import br.com.senac.ado.zoologico.security.config.PasswordEncoderConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

        // Mocka consulta de email
        when(repository.findByEmail(dto.email())).thenReturn(Optional.empty());

        // Mocka o BCrypt real retornado pelo config
        BCryptPasswordEncoder bCrypt = mock(BCryptPasswordEncoder.class);
        when(passwordEncoderConfig.bCryptPasswordEncoder()).thenReturn(bCrypt);

        // Mocka o encode do Spring PasswordEncoder
        when(bCrypt.encode(dto.senha())).thenReturn("hashed");

        // Mocka a entidade salva
        Usuario usuario = new Usuario();
        UUID id = UUID.randomUUID();
        usuario.setId(id);

        when(repository.save(any())).thenReturn(usuario);

        // Executa
        UUID result = service.saveUser(dto);

        // Asserts
        assertEquals(id, result);
        verify(repository).save(any(Usuario.class));
        verify(bCrypt).encode("123");
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

    @Test
    @DisplayName("saveAdminUser_shouldCreateAdminUser")
    void saveAdminUser_shouldCreateAdminUser() {
        UsuarioDTO dto = new UsuarioDTO("Admin", "admin@test.com", "456");

        // 1. Configura Mocks de criptografia (seguindo o padrão do seu saveUser)
        BCryptPasswordEncoder bCrypt = mock(BCryptPasswordEncoder.class);
        when(passwordEncoderConfig.bCryptPasswordEncoder()).thenReturn(bCrypt);
        when(bCrypt.encode(dto.senha())).thenReturn("hashed_admin"); // Mocka o encode

        // 2. Mocka consulta de email: retorna Optional vazio (não existe)
        when(repository.findByEmail(dto.email())).thenReturn(Optional.empty());

        // 3. Mocka a entidade salva e a verifica
        UUID idAdmin = UUID.randomUUID();
        Usuario usuarioSalvo = new Usuario();
        usuarioSalvo.setId(idAdmin);

        when(repository.save(any(Usuario.class))).thenAnswer(invocation -> {
            Usuario userToSave = invocation.getArgument(0);
            // Verifica se a Role está correta
            assertEquals(Roles.ADMIN, userToSave.getRole(), "A Role deve ser ADMIN.");
            userToSave.setId(idAdmin);
            return userToSave;
        });

        // Executa
        UUID result = service.saveAdminUser(dto);

        // Asserts
        assertEquals(idAdmin, result);
        verify(repository).save(any(Usuario.class));
        verify(bCrypt).encode("456"); // Verifica a chamada de criptografia
    }

    @Test
    @DisplayName("saveAdminUser_shouldThrowConflict")
    void saveAdminUser_shouldThrowConflict() {
        UsuarioDTO dto = new UsuarioDTO("Admin", "erick@test.com", "456"); // Usa email que já existe no setup

        // Mocka consulta de email: retorna o usuário existente (conflito)
        when(repository.findByEmail(dto.email())).thenReturn(Optional.of(usuario));

        // Asserts
        assertThrows(ConflictException.class, () -> service.saveAdminUser(dto));
        verify(repository, never()).save(any(Usuario.class)); // Garante que não salvou
    }
}
