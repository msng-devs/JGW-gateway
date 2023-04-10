package com.jaramgroupware.jaramgateway.config;

import com.jaramgroupware.jaramgateway.filters.*;
import com.jaramgroupware.jaramgateway.service.ApiRouteService;
import org.springframework.cloud.gateway.filter.factory.SetPathGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator routeLocator(ApiRouteService apiRouteService,
                                     RouteLocatorBuilder routeLocatorBuilder,
                                     AuthorizationFilterFactory authMemberFilterFactory,
                                     AuthenticationFilterFactory fireBaseAuthFilterFactory,
                                     GatewayRefreshFactory gatewayRefreshFactory,
                                     CleanRequestFilterFactory cleanRequestFilterFactory,
                                     RequestLoggingFilterFactory requestLoggingFilterFactory,
                                     SetPathGatewayFilterFactory setPathGatewayFilterFactory,
                                     ResponseLoggingFilterFactory responseLoggingFilterFactory) {
        return new RouteLocatorImpl(apiRouteService, routeLocatorBuilder,authMemberFilterFactory,fireBaseAuthFilterFactory,gatewayRefreshFactory,cleanRequestFilterFactory,setPathGatewayFilterFactory,requestLoggingFilterFactory,responseLoggingFilterFactory);
    }
}

