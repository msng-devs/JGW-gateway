package com.jaramgroupware.jaramgateway.domain.config;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ConfigRepository extends ReactiveCrudRepository<Config,Integer> {
    Mono<Config> findConfigByName(String name);
}
