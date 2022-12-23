package com.jaramgroupware.jaramgateway.config;

import com.jaramgroupware.jaramgateway.filters.*;
import com.jaramgroupware.jaramgateway.domain.apiRoute.ApiRoute;
import com.jaramgroupware.jaramgateway.service.ApiRouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.BooleanSpec;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;


@Slf4j
@RequiredArgsConstructor
public class RouteLocatorImpl implements RouteLocator {


    private final ApiRouteService apiRouteService;

    private final RouteLocatorBuilder routeLocatorBuilder;

    private final AuthorizationFilterFactory authMemberFilterFactory;

    private final AuthenticationFilterFactory fireBaseAuthFilterFactory;

    private final GatewayRefreshFactory gatewayRefreshFactory;

    private final CleanRequestFilterFactory cleanRequestFilterFactory;

    private final RequestLoggingFilterFactory requestLoggingFilterFactory;

    private final ResponseLoggingFilterFactory responseLoggingFilterFactory;

    @Override
    public Flux<Route> getRoutes() {
        RouteLocatorBuilder.Builder routesBuilder = routeLocatorBuilder.routes();
        log.info("find path (count = {}) ",apiRouteService.findAllRoute().count());
        return apiRouteService.findAllRoute()
                .map(route -> routesBuilder.route(String.valueOf(route.getId()),
                        predicateSpec -> setPredicateSpec(route, predicateSpec)))
                .collectList()
                .flatMapMany(builders -> routesBuilder.build()
                        .getRoutes());
    }

    private Buildable<Route> setPredicateSpec(ApiRoute route, PredicateSpec predicateSpec) {
        log.info("SERVICE = {} PATH ADD {} | {}",route.getService().getName(),route.getMethod().getName(),route.getPath());
        BooleanSpec booleanSpec = predicateSpec.path(route.getPath());
        booleanSpec.filters(f -> f.filters(requestLoggingFilterFactory.apply(
                config -> {if(route.isOnlyToken()) config.setEnable(true);}
        )));
        booleanSpec.filters(f -> f.filters(cleanRequestFilterFactory.apply(
                config -> {if(route.isOnlyToken()) config.setIsEnable(true);}
        )));

        //service name apply
        if (!StringUtils.isEmpty(route.getMethod().getName())) {
            booleanSpec.and()
                    .method(route.getMethod().getName());
        }

        if(route.isAuthorization()){

            booleanSpec.filters(f -> f.filters(fireBaseAuthFilterFactory.apply(
                    config -> {
                        if(route.isOnlyToken()) config.setOnlyToken(true);
                        if(route.isOptional()) config.setOptional(true);
                    }
            )));

            //if target path has role, apply authMemberFilter
            if (!StringUtils.isEmpty(route.getRole().getName())) {

                booleanSpec.filters(f -> f.filters(authMemberFilterFactory.apply(
                        config -> {config.setRole(route.getRole().getId()); }
                )));
            }

        }

        //check option, and apply option's filter
        if (route.isGatewayRefresh()) {
            booleanSpec.filters(f -> f.filters(gatewayRefreshFactory.apply(
                    config -> config.setEnable(true)
            )));
        }




        //set domain and return route
        return booleanSpec.uri(route.getService().getDomain());
    }
}
