package br.com.petfy.api_gateway.infra.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtil {

    // Pega o segredo do seu arquivo application.yml/properties
    @Value("${api.security.token.secret}")
    private String secret;

    private Key key;

    @PostConstruct
    public void init() {
        // Cria a chave de segurança uma única vez quando a aplicação sobe
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Extrai todos os "claims" (informações) de dentro do token.
     * @param token O token JWT.
     * @return Os claims do token.
     */
    public Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    /**
     * Valida o token (verifica assinatura e expiração).
     * Se o token for inválido, este método lançará uma exceção.
     * @param token O token JWT.
     */
    public void validateToken(String token) {
        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }

}
