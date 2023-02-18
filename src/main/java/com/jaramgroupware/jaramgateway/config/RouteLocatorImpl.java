package com.jaramgroupware.jaramgateway.config;

import com.jaramgroupware.jaramgateway.filters.*;
import com.jaramgroupware.jaramgateway.domain.r2dbc.apiRoute.ApiRoute;
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

    private final AuthorizationFilterFactory authorizationFilterFactory;

    private final AuthenticationFilterFactory authenticationFilterFactory;

    private final GatewayRefreshFactory gatewayRefreshFactory;

    private final CleanRequestFilterFactory cleanRequestFilterFactory;

    private final RequestLoggingFilterFactory requestLoggingFilterFactory;

    private final ResponseLoggingFilterFactory responseLoggingFilterFactory;

    @Override
    public Flux<Route> getRoutes() {
        RouteLocatorBuilder.Builder routesBuilder = routeLocatorBuilder.routes();

        return apiRouteService.findAllRoute()
                .map(route -> routesBuilder.route(String.valueOf(route.getId()),
                        predicateSpec -> setPredicateSpec(route, predicateSpec)))
                .collectList()
                .flatMapMany(builders -> routesBuilder.build()
                        .getRoutes());
    }

    private Buildable<Route> setPredicateSpec(ApiRoute route, PredicateSpec predicateSpec) {
        log.info("SERVICE = {} [{}][{}] | {}",route.getService().getName(),route.getRouteOption().getName(),route.getMethod().getName(),route.getPath());

        BooleanSpec booleanSpec = predicateSpec.path(route.getPath());

        booleanSpec.filters(f -> f.filters(requestLoggingFilterFactory.apply(
                config -> {config.setEnable(true);}
        )));

        booleanSpec.filters(f -> f.filters(cleanRequestFilterFactory.apply(
                config -> {config.setIsEnable(true);}
        )));

        if (!StringUtils.isEmpty(route.getMethod().getName())) {
            booleanSpec.and()
                    .method(route.getMethod().getName());
        }

        //option apply
        switch (route.getRouteOption().getName()){
            //오직 인증필터만
            case "AUTH":
                booleanSpec.filters(f -> f.filters(authenticationFilterFactory.apply(
                        config -> {
                            config.setOnlyToken(false);
                            config.setOptional(false);
                        }
                )));
                break;

            //오직 인증 필터만 + 토큰 인증 모드 enable
            case "ONLY_TOKEN_AUTH":
                booleanSpec.filters(f -> f.filters(authenticationFilterFactory.apply(
                        config -> {
                            config.setOnlyToken(true);
                            config.setOptional(false);
                        }
                )));
                break;

            //인증+인가 필터
            case "RBAC":
                booleanSpec.filters(f -> f.filters(authenticationFilterFactory.apply(
                        config -> {
                            config.setOnlyToken(false);
                            config.setOptional(false);
                        }
                )));

                if (!StringUtils.isEmpty(route.getRole().getName())) {

                    booleanSpec.filters(f -> f.filters(authorizationFilterFactory.apply(
                            config -> {config.setRole(route.getRole().getId()); }
                    )));
                }
                break;

            //오직 인증 필터만. Optional 옵션 enable
            case "AUTH_OPTIONAL":
                booleanSpec.filters(f -> f.filters(authenticationFilterFactory.apply(
                        config -> {
                            config.setOnlyToken(false);
                            config.setOptional(true);
                        }
                )));
                break;


            case "NO_AUTH":
            default:
                break;
        }


        //set domain and return route
        return booleanSpec.uri(route.getService().getDomain());
    }
}
