package com.jaramgroupware.jaramgateway.domain.r2dbc.role;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends ReactiveCrudRepository<Role,Integer> ,RoleCustomRepository{
}