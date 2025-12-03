/*
package br.com.senac.ado.zoologico.controller;

import br.com.senac.ado.zoologico.entity.ConsultaVeterinaria;
import br.com.senac.ado.zoologico.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ConsultaVeterinariaController.class)
class ConsultaVeterinariaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Simula a camada de Serviço, isolando o Controller
    @MockBean
    private ConsultaVeterinariaService service;

    @MockBean
    private ConsultaRelatorioService relatorioService;

    @MockBean
    private AnimalService animalService;

    // Adicionando os prováveis faltantes:
    @MockBean
    private EspecieService especieService; // PROVÁVEL NOVO
    @MockBean
    private HabitatService habitatService; // PROVÁVEL NOVO

    @MockBean
    private VeterinarioService veterinarioService;


    private static final String ENDPOINT_BUSCA_COMPOSTA = "/api/consultas/busca-composta";

    /**
     * Testa o cenário de sucesso onde consultas são encontradas com os filtros.
     */

/*
    @Test
    void buscarPorFiltros_DeveRetornarConsultasEStatusOk() throws Exception {
        // ARRANGE
        LocalDate dataConsulta = LocalDate.of(2025, 12, 10);

        // Simulação de uma ConsultaVeterinaria (simplificada)
        ConsultaVeterinaria mockConsulta = new ConsultaVeterinaria();
        mockConsulta.setDataConsulta(dataConsulta);

        List<ConsultaVeterinaria> consultasEsperadas = List.of(mockConsulta);

        // Define o comportamento do Service mockado:
        // Quando o Service for chamado com qualquer 'especialidade', 'urgente' e 'dataMin', retorne a lista mockada.
        when(service.buscarPorFiltros(
                eq("Cardiologia"),
                eq(true),
                any(LocalDate.class)))
                .thenReturn(consultasEsperadas);

        // ACT & ASSERT
        mockMvc.perform(get(ENDPOINT_BUSCA_COMPOSTA)
                        .param("especialidade", "Cardiologia") // Filtro 1: Especialidade
                        .param("urgente", "true")              // Filtro 2: Urgente
                        .param("dataMin", "2025-12-01")        // Filtro 3: Data Mínima
                        .contentType(MediaType.APPLICATION_JSON))

                // Verifica se o status HTTP é 200 OK
                .andExpect(status().isOk())
                // Verifica se o tipo de conteúdo é JSON
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Verifica se a lista retornada tem o tamanho correto (1 elemento)
                .andExpect(jsonPath("$.length()").value(1))
                // Verifica um campo específico da primeira consulta retornada (opcional, mas bom para validação)
                .andExpect(jsonPath("$[0].dataConsulta").value("2025-12-10"));
    }

    /**
     * Testa o cenário onde nenhum resultado é encontrado para os filtros.
     */


/*    @Test
    void buscarPorFiltros_DeveRetornarStatusNoContent_QuandoListaVazia() throws Exception {
        // ARRANGE
        // Define o comportamento do Service mockado:
        // Retorna uma lista vazia quando o método é chamado com quaisquer argumentos.
        when(service.buscarPorFiltros(any(String.class), any(Boolean.class), any(LocalDate.class)))
                .thenReturn(Collections.emptyList());

        // ACT & ASSERT
        mockMvc.perform(get(ENDPOINT_BUSCA_COMPOSTA)
                        .param("especialidade", "Ortopedia")
                        .param("urgente", "false")
                        .param("dataMin", "2025-01-01")
                        .contentType(MediaType.APPLICATION_JSON))

                // Verifica se o status HTTP é 204 No Content
                .andExpect(status().isNoContent())
                // Garante que o corpo da resposta está vazio
                .andExpect(content().string(""));
    }

    /**
     * Testa o cenário onde um parâmetro obrigatório é omitido (ex: 'especialidade').
     */

/*
    @Test
    void buscarPorFiltros_DeveRetornarStatusBadRequest_QuandoFaltarParametro() throws Exception {
        // ACT & ASSERT
        // Chamada sem o parâmetro "especialidade"
        mockMvc.perform(get(ENDPOINT_BUSCA_COMPOSTA)
                        .param("urgente", "true")
                        .param("dataMin", "2025-12-01")
                        .contentType(MediaType.APPLICATION_JSON))

                // Espera o status 400 Bad Request
                .andExpect(status().isBadRequest());
    }
}*/