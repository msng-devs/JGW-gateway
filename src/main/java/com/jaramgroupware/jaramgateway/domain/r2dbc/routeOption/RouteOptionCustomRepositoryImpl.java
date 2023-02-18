package com.jaramgroupware.jaramgateway.domain.r2dbc.routeOption;

import com.jaramgroupware.jaramgateway.domain.r2dbc.role.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class RouteOptionCustomRepositoryImpl implements RouteOptionCustomRepository {

    private final DatabaseClient client;
    private final RouteOptionMapper routeOptionMapper;

    @Override
    public Flux<RouteOption> findAllBy() {
        String query = "SELECT * FROM ROUTE_OPTION";

        return client.sql(query)
                .map(routeOptionMapper)
                .all();
    }
}
