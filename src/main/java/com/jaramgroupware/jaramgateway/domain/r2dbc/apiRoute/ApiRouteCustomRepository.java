package com.jaramgroupware.jaramgateway.domain.r2dbc.apiRoute;

import reactor.core.publisher.Flux;

public interface ApiRouteCustomRepository {
    Flux<ApiRoute> findAll();
    Flux<ApiRoute> findAllByServiceId(Integer id);
}
