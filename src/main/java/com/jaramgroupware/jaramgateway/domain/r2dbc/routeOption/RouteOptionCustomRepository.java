package com.jaramgroupware.jaramgateway.domain.r2dbc.routeOption;

import reactor.core.publisher.Flux;

public interface RouteOptionCustomRepository {
    Flux<RouteOption> findAllBy();
}
