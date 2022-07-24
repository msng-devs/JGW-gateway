package com.jaramgroupware.jaramgateway.domain.method;


import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MethodRepository extends ReactiveCrudRepository<Method,Integer> {
}
