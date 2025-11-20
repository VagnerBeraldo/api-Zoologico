package br.com.senac.ado.zoologico.controller;


import br.com.senac.ado.zoologico.dto.auth.LoginRequest;
import br.com.senac.ado.zoologico.dto.auth.LoginResponse;
import br.com.senac.ado.zoologico.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
            return ResponseEntity.ok(authService.login(loginRequest));
    }

//    @PostMapping("/refresh")
//    public ResponseEntity<LoginResponse> refreshToken(@RequestParam String token) {
//            return ResponseEntity.ok(authService.refreshToken(token));
//    }

}
