
package br.com.senac.ado.zoologico.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import br.com.senac.ado.zoologico.dto.auth.LoginRequest;
import br.com.senac.ado.zoologico.dto.auth.LoginResponse;
import br.com.senac.ado.zoologico.security.jwt.JwtTokenHelper;
import br.com.senac.ado.zoologico.security.userDetails.UserServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenHelper jwtTokenHelper;

    @Mock
    private UserServiceImp userServiceImp;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Teste que valida o fluxo de login quando as credenciais são válidas.
     *
     * Cenário:
     *  1. O usuário envia email e senha
     *  2. O AuthenticationManager autentica com sucesso
     *  3. O AuthService obtém os dados do usuário autenticado
     *  4. O JwtTokenHelper gera um token JWT válido
     *  5. O AuthService retorna LoginResponse contendo o token
     *
     * Objetivo:
     *  Garantir que AuthService.login() funciona conforme esperado.
     */

    @Test
    void login_shouldReturnToken_whenAuthenticationIsSuccessful() {
        // ---------- ARRANGE (Configuração do cenário de teste) ----------

        // Dados enviados pelo cliente no momento do login
        LoginRequest request = new LoginRequest("erick@test.com", "123");

        // Simulação de um usuário autenticado pelo Spring Security
        UserDetails userDetails =
                User.withUsername("erick@test.com")
                        .password("hashed")
                        .roles("USER")
                        .build();

        // Quando o metodo authentication.getPrincipal() for chamado,
        // deve retornar o UserDetails simulado acima.
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Simula que o AuthenticationManager autenticou o usuário com sucesso.
        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenReturn(authentication);

        // Simula que o token JWT foi gerado corretamente.
        when(jwtTokenHelper.generateToken(userDetails))
                .thenReturn("TOKEN_JWT_VALIDO");

        // ---------- ACT (Execução do metodo a ser testado) ----------
        LoginResponse response = authService.login(request);

        // ---------- ASSERT (Validações do resultado) ----------

        // O retorno não pode ser nulo
        assertNotNull(response);

        // O token retornado precisa ser exatamente o token mockado
        assertEquals("TOKEN_JWT_VALIDO", response.token());

        // Verifica se authenticationManager.authenticate() foi invocado corretamente
        verify(authenticationManager).authenticate(any());

        // Verifica se jwtTokenHelper.generateToken() foi chamado com o usuário correto
        verify(jwtTokenHelper).generateToken(userDetails);
    }

}
