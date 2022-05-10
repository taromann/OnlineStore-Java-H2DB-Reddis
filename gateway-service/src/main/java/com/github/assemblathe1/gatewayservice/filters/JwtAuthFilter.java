package com.github.assemblathe1.gatewayservice.filters;

import com.github.assemblathe1.gatewayservice.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

// Прогоняет чрез себя все запросы и делает валидацию и преобразование токена.
// Если хотим чтоб приходящий запрос модифицировался, то делаем extends AbstractGatewayFilterFactory.
// После чего добавляем его в application.yaml
@Component
public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {
    // если мы хотим фильтры в .yaml файле то такая структура
    // инжектим чтобы парсить и проверять токены
    private final JwtUtil jwtUtil;
    // подключаем к application.yaml (не надо разбираться зачем конфиг класс, просто такая структура)
    public JwtAuthFilter(JwtUtil jwtUtil) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
    }

    // когда запрос проходит через нас то
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest(); // получаем ссылку на request
            if (!isAuthMissing(request)) {
                final String token = getAuthHeader(request);
                System.out.println(token);
                if (jwtUtil.isInvalid(token)) {
                    return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
                }
                populateRequestWithHeaders(exchange, token);// достаем пользователя из токена
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0).substring(7);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        if (!request.getHeaders().containsKey("Authorization")) {
            return true;
        }
        if (!request.getHeaders().getOrEmpty("Authorization").get(0).startsWith("Bearer ")) {
            return true;
        }
        return false;
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        Claims claims = jwtUtil.getAllClaimsFromToken(token);
        exchange.getRequest().mutate()
                .header("username", claims.getSubject())// в наш запрос добавляем header нашего запроса информацию о юзере из токена (чтоб остальные сервисы знали от кого запрос)
//                .header("role", String.valueOf(claims.get("role")))  // - если хотим добавить роли
                .build();
    }
}
