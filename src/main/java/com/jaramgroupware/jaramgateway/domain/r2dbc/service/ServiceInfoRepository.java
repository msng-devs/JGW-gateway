package com.jaramgroupware.jaramgateway.domain.r2dbc.service;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ServiceInfoRepository extends ReactiveCrudRepository<ServiceInfo,Integer> ,ServiceInfoCustomRepository{

}
