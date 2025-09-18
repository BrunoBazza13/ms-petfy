package br.com.petfy.api_gateway.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Predicate;

@Component
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {

    public static class Config {
        // pode deixar vazia
    }

    public AuthenticationGatewayFilterFactory() {
        super(Config.class);
    }

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public GatewayFilter apply(AuthenticationGatewayFilterFactory.Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // Rotas públicas que não precisam de token (ex: login, cadastro)
            final List<String> publicEndpoints = List.of("/auth/login", "/usuarios/cadastro");

            Predicate<ServerHttpRequest> isPublic = r -> publicEndpoints.stream()
                    .anyMatch(uri -> r.getURI().getPath().contains(uri));

            if (isPublic.test(request)) {
                return chain.filter(exchange); // Deixa passar requisições para rotas públicas
            }

            // Verifica se o cabeçalho de autorização existe
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            // Pega o token do cabeçalho
            String authHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                authHeader = authHeader.substring(7); // Remove o "Bearer "
            }

            // Valida o token
            try {
                jwtUtil.validateToken(authHeader);
            } catch (Exception e) {
                System.out.println("Token inválido: " + e.getMessage());
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            // Se o token for válido, adiciona o ID do usuário no cabeçalho da requisição
            // para os outros microserviços saberem quem é o usuário.
            String userId = jwtUtil.getClaims(authHeader).getSubject();
            ServerHttpRequest mutatedRequest = request.mutate()
                    .header("X-User-ID", userId)
                    .build();

            return chain.filter(exchange.mutate().request(mutatedRequest).build());
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    // Classe de configuração vazia, necessária para o Factor

}
