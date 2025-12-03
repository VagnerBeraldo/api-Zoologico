package br.com.senac.ado.zoologico.controller;

import br.com.senac.ado.zoologico.dto.Usuario.UsuarioDTO;
import br.com.senac.ado.zoologico.entity.Usuario;
import br.com.senac.ado.zoologico.exception.GlobalExceptionHandler;
import br.com.senac.ado.zoologico.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @InjectMocks
    private UsuarioController controller;

    @Mock
    private UsuarioService usuarioService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler()) // se tiver
                .build();
    }

    @Test
    void listAll_shouldReturn200() throws Exception {
        Usuario u = new Usuario();
        u.setId(UUID.randomUUID());

        when(usuarioService.getAll()).thenReturn(List.of(u));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(u.getId().toString()));
    }

    @Test
    void findById_shouldReturn200() throws Exception {
        UUID id = UUID.randomUUID();
        Usuario u = new Usuario();
        u.setId(id);

        when(usuarioService.findById(id)).thenReturn(u);

        mockMvc.perform(get("/api/usuarios/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    void saveUser_shouldReturn201() throws Exception {
        UsuarioDTO dto = new UsuarioDTO("user", "email@test.com", "1234");
        UUID id = UUID.randomUUID();

        when(usuarioService.saveUser(dto)).thenReturn(id);

        mockMvc.perform(post("/api/usuarios/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/usuarios/" + id));
    }

    @Test
    void update_shouldReturn204() throws Exception {
        UUID id = UUID.randomUUID();
        UsuarioDTO dto = new UsuarioDTO("user", "email@test.com", "1234");

        doNothing().when(usuarioService).update(eq(id), any());

        mockMvc.perform(put("/api/usuarios/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_shouldReturn204() throws Exception {
        UUID id = UUID.randomUUID();

        doNothing().when(usuarioService).deleteById(id);

        mockMvc.perform(delete("/api/usuarios/{id}", id))
                .andExpect(status().isNoContent());
    }
}
