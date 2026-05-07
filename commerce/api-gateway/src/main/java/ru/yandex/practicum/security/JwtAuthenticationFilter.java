package ru.yandex.practicum.security;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter
        implements GlobalFilter, Ordered {

    private final JwtService jwtService;

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain
    ) {

        String path =
                exchange.getRequest()
                        .getURI()
                        .getPath();

        if (path.startsWith("/auth")) {
            return chain.filter(exchange);
        }

        String header =
                exchange.getRequest()
                        .getHeaders()
                        .getFirst(HttpHeaders.AUTHORIZATION);

        System.out.println("AUTH HEADER = " + header);

        if (header == null
                || !header.startsWith("Bearer ")) {

            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse()
                    .setComplete();
        }

        String token = header.substring(7);

        System.out.println("TOKEN = " + token);

        if (!jwtService.isValid(token)) {

            System.out.println("TOKEN INVALID");

            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);

            return exchange.getResponse()
                    .setComplete();
        }

        System.out.println("TOKEN VALID");

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}