package com.jaramgroupware.jaramgateway.domain.apiRoute;

import com.jaramgroupware.jaramgateway.domain.method.Method;
import com.jaramgroupware.jaramgateway.domain.role.Role;
import com.jaramgroupware.jaramgateway.domain.service.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class ApiRouteCustomRepositoryImpl implements ApiRouteCustomRepository {

    private final DatabaseClient client;
    private final ApiRouteMapper apiRouteMapper;

    @Override
    public Flux<ApiRoute> findAll() {
        String query = "SELECT * FROM API_ROUTE\n" +
                "LEFT JOIN METHOD\n" +
                "ON METHOD.METHOD_PK = API_ROUTE.METHOD_METHOD_PK\n" +
                "LEFT JOIN ROLE\n" +
                "ON ROLE.ROLE_PK = API_ROUTE.ROLE_ROLE_PK\n" +
                "LEFT JOIN SERVICE\n" +
                "ON SERVICE.SERVICE_PK = API_ROUTE.SERVICE_SERVICE_PK";

        return client.sql(query)
                .map(apiRouteMapper::apply)
                .all();
    }

}
