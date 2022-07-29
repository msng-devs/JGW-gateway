package com.jaramgroupware.jaramgateway.domain.Error;

import reactor.core.publisher.Mono;

public interface ErrorInfoCustomRepository {
    Mono<ErrorInfo> findByID(Integer id);
    Mono<ErrorInfo> findByName(String name);
}
