package com.jaramgroupware.jaramgateway.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * ref : https://stackoverflow.com/questions/53144055/csrf-token-not-generated-with-webflux
 */
public class CsrfHeaderFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String xsrfToken = Objects.requireNonNull(exchange.getRequest().getCookies().getFirst("XSRF-TOKEN")).getValue();

        ServerWebExchange newExchange = exchange.mutate().request(
            req -> {
                req.header("X-XSRF-TOKEN", xsrfToken);
            }
        ).build();
        chain.filter(newExchange);

        return null;
    }
}
