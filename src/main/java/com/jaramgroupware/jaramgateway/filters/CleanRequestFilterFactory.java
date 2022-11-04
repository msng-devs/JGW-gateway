package com.jaramgroupware.jaramgateway.filters;


import com.jaramgroupware.jaramgateway.utils.ErrorResponseCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;


@Component
@RequiredArgsConstructor
public class CleanRequestFilterFactory implements GatewayFilterFactory<CleanRequestFilterFactory.Config> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ErrorResponseCreator errorResponseCreator;

    @Getter
    @Setter
    @Validated
    public static class Config {

        @NotEmpty
        private Boolean isEnable;

    }

    @Override
    public Config newConfig() {

        return new Config();
    }


    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            ServerHttpRequest newRequest = exchange.getRequest()
                    .mutate()
                    .header("user_pk", "")
                    .header("role_pk", "")
                    .build();

            return chain.filter(exchange.mutate().request(newRequest).build());


        });
    }
}