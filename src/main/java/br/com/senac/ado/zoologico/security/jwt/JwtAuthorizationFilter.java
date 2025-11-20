package br.com.senac.ado.zoologico.security.jwt;

import br.com.senac.ado.zoologico.security.userDetails.UserServiceImp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenHelper jwtTokenHelper;
    private final UserServiceImp userServiceImp;

    private static final String HEADER_PREFIX = "Bearer ";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        //Pega o cabeçalho Authorization da requisição
        Optional<String> tokenOptional  = extractTokenFromHeader(request);

        if (tokenOptional .isEmpty()) {
            // Se não há token, esta não é uma requisição que não posso autenticar.
            // Apenas continua a cadeia de filtros. Se o endpoint for protegido,
            // os filtros posteriores do Spring Security vão bloquear.
            filterChain.doFilter(request, response);
            return;
        }

        String token =  tokenOptional.get();



        try {

            String username = jwtTokenHelper.extractUserName(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = userServiceImp.loadUserByUsername(username);

                if (jwtTokenHelper.isTokenValid(token, userDetails)) {

                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(
                              userDetails, null, userDetails.getAuthorities()
                            );

                   auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                   SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }


        } catch (Exception e) {
            log.warn("Erro ao validar o token JWT: " + e.getMessage());
        }

        filterChain.doFilter(request, response);


    }

    /**
     * Metodo auxiliar para extrair o token JWT (sem o "Bearer ")
     * do cabeçalho "Authorization".
     */
    private Optional<String> extractTokenFromHeader(HttpServletRequest request) {
        var header = request.getHeader("Authorization");

        //VERIFICA SE O CABEÇALHO COMEÇA COM "Bearer "
        if (header != null && header.startsWith(HEADER_PREFIX)) {
            // RETORNA APENAS O TOKEN (remove o "Bearer ")
            return Optional.of(header.substring(HEADER_PREFIX.length()));
        }
        return Optional.empty();
    }
}
