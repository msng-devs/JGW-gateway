package com.jaramgroupware.jaramgateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 게이트웨이에서 로깅을 담당하는 global filter 입니다. <br>
 * 모든 필터에서 가장 먼저 실행되며, 해당 요청을 보낸 IP, 그리고 요청을 로깅합니다.<br>
 * @author hrabit64(37기 황준서)
 * @version 1.0
 * @since 1.0
 */
@Component
public class LoggingFilter implements GlobalFilter, Ordered {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public GlobalFilter customFilter() {
        return new LoggingFilter();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("IP: {} Request: {} get new request.",exchange.getRequest().getLocalAddress(),exchange.getRequest().getURI());
        logger.info("headers = {}",exchange.getRequest().getHeaders().toString());
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}

