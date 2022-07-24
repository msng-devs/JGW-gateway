package com.jaramgroupware.jaramgateway.domain.apiRoute;

import reactor.core.publisher.Flux;

public interface ApiRouteCustomRepository {
    Flux<ApiRoute> findAll();
}
