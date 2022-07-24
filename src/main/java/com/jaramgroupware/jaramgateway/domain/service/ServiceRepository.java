package com.jaramgroupware.jaramgateway.domain.service;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends ReactiveCrudRepository<Service,Integer> {
}
