package com.example.apigateway.filter;

import com.example.apigateway.util.RedisUtil;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private final RedisUtil redisUtil;

    public AuthorizationHeaderFilter(RedisUtil redisUtil) {
        super(Config.class);
        this.redisUtil = redisUtil;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }

            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String token = authorizationHeader.replace("Bearer ", "");

            log.info("token " + token);

            if (!isValid(token) || redisUtil.existData(token)) {
                return onError(exchange, "Token is not valid", HttpStatus.UNAUTHORIZED);
            }
            return chain.filter(exchange);
        }));
    }

    private boolean isValid(String token) {
        boolean value = true;
        String subject = null;

        try {
            subject = Jwts.parser().setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token).getBody()
                    .getSubject();
        } catch (Exception e) {
            value = false;
        }

        if (subject == null || subject.isEmpty()) {
            value = false;
        }
        return value;
    }

    private Mono<Void> onError(ServerWebExchange exchange, String error, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        log.error(error);
        return response.setComplete();
    }

    public static class Config {
    }
}
