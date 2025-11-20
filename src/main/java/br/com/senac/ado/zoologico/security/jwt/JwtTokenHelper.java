package br.com.senac.ado.zoologico.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JwtTokenHelper {

    private static final ZoneId ZONE = ZoneId.of("America/Sao_Paulo");

    // CHAVE SECRETA — precisa ter 64+ caracteres para HS512
    private static final String SECRET =
            "CHAVE_SECRETA_MUITO_SECRETA_PRA_CARAI_MEMO_64CARACTERES_MINIMO_SEGURA_2025";

    private final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    // Token: 4 horas
    private static final long ACCESS_TOKEN_EXP = Duration.ofHours(1).toMillis();

    // Refresh token: 7 dias
    private static final long REFRESH_TOKEN_EXP = Duration.ofDays(7).toMillis();



    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(nowDate())
                .expiration(addMillis(ACCESS_TOKEN_EXP))
                .claim("role", userDetails.getAuthorities().iterator().next().getAuthority())
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(nowDate())
                .expiration(addMillis(REFRESH_TOKEN_EXP))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }



    public String extractUserName(String token) {
        return getClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(nowDate());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUserName(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }


    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Instant now() {
        return ZonedDateTime.now(ZONE).toInstant();
    }

    private Date nowDate() {
        return Date.from(now());
    }

    private Date addMillis(long millis) {
        return Date.from(now().plusMillis(millis));
    }




}
