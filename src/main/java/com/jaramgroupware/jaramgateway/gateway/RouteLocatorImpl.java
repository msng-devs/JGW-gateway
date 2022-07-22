package com.jaramgroupware.jaramgateway.gateway;

import com.jaramgroupware.jaramgateway.config.filters.AuthMemberFilterFactory;
import com.jaramgroupware.jaramgateway.domain.apiRoute.ApiRoute;
import com.jaramgroupware.jaramgateway.service.ApiRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.BooleanSpec;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;


/**
 * ref:https://medium.com/bliblidotcom-techblog/spring-cloud-gateway-dynamic-routes-from-database-dc938c6665de
 */
@RequiredArgsConstructor
public class RouteLocatorImpl implements RouteLocator {

    @Autowired
    private final ApiRouteService routeService;

    @Autowired
    private final RouteLocatorBuilder routeLocatorBuilder;

    @Autowired
    private final AuthMemberFilterFactory authMemberFilterFactory;


    @Override
    public Flux<Route> getRoutes() {
        RouteLocatorBuilder.Builder routesBuilder = routeLocatorBuilder.routes();
        return routeService.findAllRoute()
                .map(route -> routesBuilder.route(String.valueOf(route.getId()),
                        predicateSpec -> setPredicateSpec(route, predicateSpec)))
                .collectList()
                .flatMapMany(builders -> routesBuilder.build()
                        .getRoutes());
    }

    private Buildable<Route> setPredicateSpec(ApiRoute route, PredicateSpec predicateSpec) {

        BooleanSpec booleanSpec = predicateSpec.path(route.getPath());


        //service name apply
        if (!StringUtils.isEmpty(route.getMethod().getName())) {
            booleanSpec.and()
                    .method(route.getMethod().getName());
        }

        //if target path has role, apply authMemberFilter
        if (!StringUtils.isEmpty(route.getRole().getName())) {
            booleanSpec.filters(f -> f.filters(authMemberFilterFactory.apply(
                    config -> config.setRole(route.getRole().getId())
            )));
        }


        //set domain and return route
        return booleanSpec.uri(route.getService().getDomain());
    }
}
