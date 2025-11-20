package br.com.senac.ado.zoologico.service;


import br.com.senac.ado.zoologico.dto.auth.LoginRequest;
import br.com.senac.ado.zoologico.dto.auth.LoginResponse;
import br.com.senac.ado.zoologico.security.jwt.JwtTokenHelper;
import br.com.senac.ado.zoologico.security.userDetails.UserServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenHelper jwtTokenHelper;
    private final UserServiceImp userServiceImp;

    public LoginResponse login(LoginRequest request) {
        var authToken = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication auth = authenticationManager.authenticate(authToken);

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String accessToken = jwtTokenHelper.generateToken(userDetails);
        //String refreshToken = jwtTokenHelper.generateRefreshToken(userDetails);

        //Nas proximas entregas, implementar refresh token e ver como armazenar ele no front-end
        //return new LoginResponse(accessToken, refreshToken);
        return new LoginResponse(accessToken);
    }

//    public LoginResponse refreshToken(String refreshToken) {
//        String username = jwtTokenHelper.extractUserName(refreshToken);
//        UserDetails userDetails = userServiceImp.loadUserByUsername(username);
//
//        if (!jwtTokenHelper.isTokenValid(refreshToken, userDetails)) {
//            throw new RuntimeException("Refresh token inválido ou expirado");
//        }
//
//        String accessToken = jwtTokenHelper.generateToken(userDetails);
//        String newRefreshToken = jwtTokenHelper.generateRefreshToken(userDetails);
//
//
//
//        return new LoginResponse(accessToken, refreshToken);
//    }

}
