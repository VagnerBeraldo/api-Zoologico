package br.com.senac.ado.zoologico.controller;

import br.com.senac.ado.zoologico.dto.Usuario.UsuarioDTO;
import br.com.senac.ado.zoologico.entity.Usuario;
import br.com.senac.ado.zoologico.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UsuarioService service;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void listAll_shouldReturn200() throws Exception {
        Usuario u = new Usuario();
        u.setId(UUID.randomUUID());

        Mockito.when(service.getAll()).thenReturn(List.of(u));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk());
    }

    @Test
    void findById_shouldReturn200() throws Exception {
        UUID id = UUID.randomUUID();
        Usuario u = new Usuario();
        u.setId(id);

        Mockito.when(service.findById(id)).thenReturn(u);

        mockMvc.perform(get("/api/usuarios/" + id))
                .andExpect(status().isOk());
    }

    @Test
    void saveUser_shouldReturn201() throws Exception {
        UsuarioDTO dto = new UsuarioDTO("user", "email@test.com", "1234");
        UUID id = UUID.randomUUID();

        Mockito.when(service.saveUser(dto)).thenReturn(id);

        mockMvc.perform(post("/api/usuarios/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void update_shouldReturn204() throws Exception {
        UsuarioDTO dto = new UsuarioDTO("user", "email@test.com", "1234");

        mockMvc.perform(put("/api/usuarios/" + UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_shouldReturn204() throws Exception {
        mockMvc.perform(delete("/api/usuarios/" + UUID.randomUUID()))
                .andExpect(status().isNoContent());
    }
}
