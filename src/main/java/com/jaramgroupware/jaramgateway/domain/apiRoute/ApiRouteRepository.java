package com.jaramgroupware.jaramgateway.domain.apiRoute;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ApiRouteRepository extends ReactiveCrudRepository<ApiRoute,Integer> {
    ApiRoute findRouteById(Integer id);
    Flux<ApiRoute> findAllBy();
}
