package com.jaramgroupware.jaramgateway.domain.r2dbc.routeOption;

import com.jaramgroupware.jaramgateway.domain.r2dbc.role.Role;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteOptionRepository extends ReactiveCrudRepository<RouteOption,Integer>,RouteOptionCustomRepository {
}
