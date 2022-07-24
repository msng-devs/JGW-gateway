package com.jaramgroupware.jaramgateway.domain.apiRoute;



import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ApiRouteRepository extends ReactiveCrudRepository<ApiRoute,Integer> , ApiRouteCustomRepository{
}
