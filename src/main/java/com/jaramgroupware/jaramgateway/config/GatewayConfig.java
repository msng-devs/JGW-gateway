package com.jaramgroupware.jaramgateway.config;

import com.jaramgroupware.jaramgateway.filters.AuthorizationFilterFactory;
import com.jaramgroupware.jaramgateway.filters.AuthenticationFilterFactory;
import com.jaramgroupware.jaramgateway.filters.GatewayRefreshFactory;
import com.jaramgroupware.jaramgateway.service.ApiRouteService;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 자람 그룹웨어의 Gateway의 설정 클래스입니다.
 * RouteLocatorImpl 클래스를 적용시킵니다.
 *
 * @author hrabit64(37기 황준서)
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator routeLocator(ApiRouteService apiRouteService,
                                     RouteLocatorBuilder routeLocatorBuilder,
                                     AuthorizationFilterFactory authMemberFilterFactory,
                                     AuthenticationFilterFactory fireBaseAuthFilterFactory,
                                     GatewayRefreshFactory gatewayRefreshFactory) {
        return new RouteLocatorImpl(apiRouteService, routeLocatorBuilder,authMemberFilterFactory,fireBaseAuthFilterFactory,gatewayRefreshFactory);
    }
}

