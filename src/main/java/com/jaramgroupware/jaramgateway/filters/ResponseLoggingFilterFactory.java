package com.jaramgroupware.jaramgateway.filters;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Component
@RequiredArgsConstructor
public class ResponseLoggingFilterFactory implements GatewayFilterFactory<ResponseLoggingFilterFactory.Config> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Getter
    @Setter
    @Validated
    public static class Config {
        @NotEmpty
        private boolean isEnable;

    }

    @Override
    public Config newConfig() {

        return new Config();
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            logger.info("IP: {} Request: {} fin. Status = {}",exchange.getRequest().getLocalAddress(),exchange.getRequest().getURI(),exchange.getResponse().getRawStatusCode());
            return chain.filter(exchange);
        });
    }
}
