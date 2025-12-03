/*
package br.com.senac.ado.zoologico.integration;


import br.com.senac.ado.zoologico.dto.Usuario.UsuarioDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UsuarioIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void shouldCreateAndGetUser() throws Exception {

        UsuarioDTO dto = new UsuarioDTO("user", "email@test123.com", "1234");

        String location = mockMvc.perform(post("/api/usuarios/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader("Location");

        assertNotNull(location);

        mockMvc.perform(get(location))
                .andExpect(status().isOk());
    }
}
*/

package br.com.senac.ado.zoologico.integration;

import br.com.senac.ado.zoologico.dto.Usuario.UsuarioDTO;
import br.com.senac.ado.zoologico.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UsuarioIntegrationTest {

    // Injeção de dependências
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    // NOVIDADE: Injeção do Repositório para a limpeza
    @Autowired
    private UsuarioRepository usuarioRepository;

    // NOVIDADE: Método de limpeza executado antes de cada teste
    @BeforeEach
    void setup() {
        // Garante que o banco de dados esteja limpo antes de cada teste
        // para evitar erros de conflito (409) por duplicação de chaves únicas.
        usuarioRepository.deleteAll();
    }

    // --- TESTES ---

    @Test
    void shouldCreateAndGetUser() throws Exception {

        // Uso de UUID para garantir um email único, complementando a limpeza
        String uniqueEmail = "user_" + UUID.randomUUID().toString().substring(0, 8) + "@test.com";

        UsuarioDTO dto = new UsuarioDTO("newUser", uniqueEmail, "12345");

        // 1. Tenta criar o usuário (Esperado: 201 Created)
        String location = mockMvc.perform(post("/api/usuarios/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isCreated()) // Ponto que estava falhando (linha ~34)
                .andReturn()
                .getResponse()
                .getHeader("Location");

        assertNotNull(location);

        // 2. Tenta buscar o usuário pelo Location Header (Esperado: 200 Ok)
        mockMvc.perform(get(location))
                .andExpect(status().isOk())
                // Opcional: Verifica se o email retornado é o mesmo
                .andExpect(jsonPath("$.email").value(uniqueEmail));
    }

    @Test
    void shouldReturnConflictOnDuplicateEmail() throws Exception {
        // 1. Cria um usuário (Setup)
        UsuarioDTO existingDto = new UsuarioDTO("ExistUser", "duplicate@test.com", "1234");

        mockMvc.perform(post("/api/usuarios/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(existingDto)))
                .andExpect(status().isCreated());

        // 2. Tenta criar o mesmo usuário novamente (Teste real)
        UsuarioDTO duplicateDto = new UsuarioDTO("AnotherUser", "duplicate@test.com", "9999");

        mockMvc.perform(post("/api/usuarios/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(duplicateDto)))
                // Espera o 409 Conflict
                .andExpect(status().isConflict());
    }
}