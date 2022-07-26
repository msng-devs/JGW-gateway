package com.jaramgroupware.jaramgateway.filters;


import com.jaramgroupware.jaramgateway.utils.GatewayRefresh;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;


@Component
@RequiredArgsConstructor
public class GatewayRefreshFactory implements GatewayFilterFactory<GatewayRefreshFactory.Config> {

    @Autowired
    private final GatewayRefresh gatewayRefresh;

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
            gatewayRefresh.refreshRoutes();
            logger.info("IP: {} Uid: {} run refresh.",exchange.getRequest().getLocalAddress(),exchange.getRequest().getHeaders().get("user_uid"));
            return chain.filter(exchange);

        });
    }
}