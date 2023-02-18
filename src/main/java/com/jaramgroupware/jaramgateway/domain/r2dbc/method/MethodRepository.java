package com.jaramgroupware.jaramgateway.domain.r2dbc.method;


import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MethodRepository extends ReactiveCrudRepository<Method,Integer>,MethodCustomRepository {

}
