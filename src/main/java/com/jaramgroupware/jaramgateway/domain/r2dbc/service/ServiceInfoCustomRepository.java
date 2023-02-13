package com.jaramgroupware.jaramgateway.domain.r2dbc.service;

import reactor.core.publisher.Flux;

public interface ServiceInfoCustomRepository {
    Flux<ServiceInfo> findAllBy();
}
