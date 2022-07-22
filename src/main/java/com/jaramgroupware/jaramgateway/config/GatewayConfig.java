package com.jaramgroupware.jaramgateway.config;

import com.jaramgroupware.jaramgateway.config.filters.AuthMemberFilterFactory;
import com.jaramgroupware.jaramgateway.gateway.RouteLocatorImpl;
import com.jaramgroupware.jaramgateway.service.ApiRouteService;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator routeLocator(ApiRouteService apiRouteService,
                                     RouteLocatorBuilder routeLocatorBuilder, AuthMemberFilterFactory authMemberFilterFactory) {
        return new RouteLocatorImpl(apiRouteService, routeLocatorBuilder,authMemberFilterFactory);
    }
}

