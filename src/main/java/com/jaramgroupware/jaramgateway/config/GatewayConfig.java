package com.jaramgroupware.jaramgateway.config;

import com.jaramgroupware.jaramgateway.config.filters.AuthMemberFilterFactory;
import com.jaramgroupware.jaramgateway.config.filters.FireBaseAuthFilterFactory;
import com.jaramgroupware.jaramgateway.gateway.RouteLocatorImpl;
import com.jaramgroupware.jaramgateway.service.ApiRouteService;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring cloud Gateway Config 클래스. RouteLocatorImpl를 통해 route를 등록
 */
@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator routeLocator(ApiRouteService apiRouteService,
                                     RouteLocatorBuilder routeLocatorBuilder,
                                     AuthMemberFilterFactory authMemberFilterFactory,
                                     FireBaseAuthFilterFactory fireBaseAuthFilterFactory) {
        return new RouteLocatorImpl(apiRouteService, routeLocatorBuilder,authMemberFilterFactory,fireBaseAuthFilterFactory);
    }
}

