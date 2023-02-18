package com.jaramgroupware.jaramgateway.domain.r2dbc.method;


import reactor.core.publisher.Flux;

public interface MethodCustomRepository {
    Flux<Method> findAllBy();
}
