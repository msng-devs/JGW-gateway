package com.jaramgroupware.jaramgateway.domain.r2dbc.role;

import com.jaramgroupware.jaramgateway.domain.r2dbc.method.MethodMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class RoleCustomRepositoryImpl implements RoleCustomRepository {

    private final DatabaseClient client;
    private final RoleMapper roleMapper;

    @Override
    public Flux<Role> findAllBy() {
        String query = "SELECT * FROM ROLE";

        return client.sql(query)
                .map(roleMapper)
                .all();
    }
}
