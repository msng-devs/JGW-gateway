package com.jaramgroupware.jaramgateway.domain.r2dbc.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ServiceInfoCustomRepository {
    Flux<ServiceInfo> findAllBy();
    Mono<ServiceInfo> findServiceById(Integer id);
}
