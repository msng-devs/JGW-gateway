package com.jaramgroupware.jaramgateway.gateway;

import com.jaramgroupware.jaramgateway.config.filters.AuthMemberFilterFactory;
import com.jaramgroupware.jaramgateway.config.filters.FireBaseAuthFilterFactory;
import com.jaramgroupware.jaramgateway.config.filters.GatewayRefreshFactory;
import com.jaramgroupware.jaramgateway.domain.apiRoute.ApiRoute;
import com.jaramgroupware.jaramgateway.service.ApiRouteService;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.RouteRefreshListener;
import org.springframework.cloud.gateway.route.builder.BooleanSpec;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;


/**
 * APIRoute 테이블에 있는 route들을 자동으로 등록하는 클래스
 * ref:https://medium.com/bliblidotcom-techblog/spring-cloud-gateway-dynamic-routes-from-database-dc938c6665de
 */
@RequiredArgsConstructor
public class RouteLocatorImpl implements RouteLocator {


    private final ApiRouteService apiRouteService;

    private final RouteLocatorBuilder routeLocatorBuilder;

    private final AuthMemberFilterFactory authMemberFilterFactory;

    private final FireBaseAuthFilterFactory fireBaseAuthFilterFactory;

    private final GatewayRefreshFactory gatewayRefreshFactory;

    /**
     * 모든 route를 가져와서 등록
     * @return
     */
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

    /**
     * 해당 route의 설정을 확인하여 적절한 Path, Predicate와 filter를 등록하는 클래스
     * @param route
     * @param predicateSpec
     * @return
     */
    private Buildable<Route> setPredicateSpec(ApiRoute route, PredicateSpec predicateSpec) {

        BooleanSpec booleanSpec = predicateSpec.path(route.getPath());

        //service name apply
        if (!StringUtils.isEmpty(route.getMethod().getName())) {
            booleanSpec.and()
                    .method(route.getMethod().getName());
        }

        //if target path has role, apply authMemberFilter
        //but it has guest role, not apply authMemberFilter
        if (!StringUtils.isEmpty(route.getRole().getName()) && route.getRole().getId() != 1) {

            booleanSpec.filters(f -> f.filters(fireBaseAuthFilterFactory.apply(
                    config -> config.setEnable(true)
            )));

            booleanSpec.filters(f -> f.filters(authMemberFilterFactory.apply(
                    config -> config.setRole(route.getRole().getId())
            )));

        }

        //if it is refresh route, add refresh filter
        if (route.getPath().equals("/gateway/refresh")) {
            booleanSpec.filters(f -> f.filters(gatewayRefreshFactory.apply(
                    config -> config.setEnable(true)
            )));
        }




        //set domain and return route
        return booleanSpec.uri(route.getService().getDomain());
    }
}
