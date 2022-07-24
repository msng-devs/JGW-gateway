package com.jaramgroupware.jaramgateway.domain.role;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends ReactiveCrudRepository<Role,Integer> {
    Role findRoleById (Integer id);
}
