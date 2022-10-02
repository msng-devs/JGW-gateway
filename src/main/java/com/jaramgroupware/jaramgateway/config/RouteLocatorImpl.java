package com.jaramgroupware.jaramgateway.config;

import com.jaramgroupware.jaramgateway.filters.AuthorizationFilterFactory;
import com.jaramgroupware.jaramgateway.filters.AuthenticationFilterFactory;
import com.jaramgroupware.jaramgateway.filters.GatewayRefreshFactory;
import com.jaramgroupware.jaramgateway.domain.apiRoute.ApiRoute;
import com.jaramgroupware.jaramgateway.service.ApiRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.BooleanSpec;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

/**
 * 자람 그룹웨어의 Gateway의 route 등록 클래스입니다.
 * JGW database의 API_ROUTE 테이블에 있는 라우팅 정보들을 불러와 gateway의 route로 등록하고, 설정 정보에 따라 적절한 필터를 적용시킵니다.
 *
 * ref:https://medium.com/bliblidotcom-techblog/spring-cloud-gateway-dynamic-routes-from-database-dc938c6665de
 * @author hrabit64(37기 황준서)
 * @version 1.0
 * @since 1.0
 */
@RequiredArgsConstructor
public class RouteLocatorImpl implements RouteLocator {


    private final ApiRouteService apiRouteService;

    private final RouteLocatorBuilder routeLocatorBuilder;

    private final AuthorizationFilterFactory authMemberFilterFactory;

    private final AuthenticationFilterFactory fireBaseAuthFilterFactory;

    private final GatewayRefreshFactory gatewayRefreshFactory;

    /**
     * 모든 API_ROUTE 를 찾아와 gateway의 route로 등록하는 클래스,
     * ApiRouteService 클래스를 통해 모든 route 정보를 찾아오고, setPredicateSpec 클래스를 통해, path, filter, domain,service 등을 등록합니다.
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
     * route에 대한 정보를 바탕으로, path, filter, domain을 등록하는 클래스
     *
     * 아래와 같은 순서로 route에 정보를 등록합니다.
     *
     * 1. ApiRoute.path (API_ROUTE_PATH)에 대한 정보로 해당 route의 경로를 등록합니다.
     * 2. ApiRoute.Role (ROLE_ROLE_PK)에 대한 정보를 확인하고, 만약 Role에 대한 정보가 있다면 아래 과정을 수행합니다.
     *      2-1. fireBaseAuthFilter(firebase 인증 필터)를 등록합니다.
     *      2-2. authMemberFilter(유저 인증 및 인가 필터)를 등록합니다. Config에 ApiRoute.Role.id를 등록하고,
     *      만약 ApiRoute.isAddUserInfo (API_ROUTE_ADD_USER_INFO)가 true라면 (= request에 유저 정보를 등록하길 원함) config에 AddUserInfo를 활성화 시킵니다.,
     * 3. ApiRoute.isGatewayRefresh (API_ROUTE_GATEWAY_REFRESH)가 true라면(= 해당 route를 처리할때 route 정보를 갱신하길 원함) gatewayRefresh 필터를 적용시킵니다.
     *
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

        if(route.isAuthorization()){

            booleanSpec.filters(f -> f.filters(fireBaseAuthFilterFactory.apply(
                    config -> {if(route.isOnlyToken()) config.setOnlyToken(true);}
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
