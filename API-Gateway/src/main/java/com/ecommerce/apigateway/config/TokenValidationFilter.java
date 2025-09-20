package com.ecommerce.apigateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.HashMap;

@Component
public class TokenValidationFilter implements GlobalFilter, Ordered {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        if (path.startsWith("/auth/login") || path.startsWith("/auth/register")) {
            return chain.filter(exchange);
        }

        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (token == null || !token.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String finalToken = token.substring(7);

        return Mono.fromCallable(() -> {
            System.out.print("IDP-SERIVCE");
                    ResponseEntity<HashMap> response = restTemplate.getForEntity(
                            "http://IDENTITY-PROVIDER/auth/" + finalToken,
                            HashMap.class
                    );

                    HashMap body = response.getBody();
                    System.out.println("🔑 IDP Response: " + body);

                    return body;
                })
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(body -> {
                    if (body == null) {
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    }

                    String status = String.valueOf(body.get("status"));
                    if ("true".equalsIgnoreCase(status)) {
                        String userId = String.valueOf(body.get("userId"));



                        ServerWebExchange mutatedExchange = exchange.mutate()
                                .request(r -> r.headers(h -> h.set("X-User-Id", userId)))
                                .build();

                        return chain.filter(mutatedExchange);
                    } else {

                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    }
                })
                .onErrorResume(e -> {
                    System.out.println(" Error contacting IDP: " + e.getMessage());
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                });
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
