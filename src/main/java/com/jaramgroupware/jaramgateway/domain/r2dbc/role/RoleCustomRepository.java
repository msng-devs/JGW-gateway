package com.jaramgroupware.jaramgateway.domain.r2dbc.role;

import com.jaramgroupware.jaramgateway.domain.r2dbc.method.Method;
import reactor.core.publisher.Flux;

public interface RoleCustomRepository {
    Flux<Role> findAllBy();
}
