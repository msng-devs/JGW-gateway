package com.jaramgroupware.jaramgateway.config.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LoggingFilter implements GlobalFilter, Ordered {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Bean
    public GlobalFilter customFilter() {
        return new LoggingFilter();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("global {}",exchange.getRequest().getLocalAddress());
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}

