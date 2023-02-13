package com.jaramgroupware.jaramgateway.domain.r2dbc.apiRoute;

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
                "ON SERVICE.SERVICE_PK = API_ROUTE.SERVICE_SERVICE_PK\n"+
                "LEFT JOIN ROUTE_OPTION\n" +
                "ON ROUTE_OPTION.ROUTE_OPTION_PK = API_ROUTE.ROUTE_OPTION_ROUTE_OPTION_PK\n";

        return client.sql(query)
                .map(apiRouteMapper)
                .all();
    }

    @Override
    public Flux<ApiRoute> findAllByServiceId(Integer id) {

        String query = "SELECT * FROM API_ROUTE\n" +
                "LEFT JOIN METHOD\n" +
                "ON METHOD.METHOD_PK = API_ROUTE.METHOD_METHOD_PK\n" +
                "LEFT JOIN ROLE\n" +
                "ON ROLE.ROLE_PK = API_ROUTE.ROLE_ROLE_PK\n" +
                "LEFT JOIN SERVICE\n" +
                "ON SERVICE.SERVICE_PK = API_ROUTE.SERVICE_SERVICE_PK\n"+
                "LEFT JOIN ROUTE_OPTION\n" +
                "ON ROUTE_OPTION.ROUTE_OPTION_PK = API_ROUTE.ROUTE_OPTION_ROUTE_OPTION_PK\n"+
                "WHERE SERVICE_PK = ?";

        return client.sql(query)
                .bind(0,id)
                .map(apiRouteMapper)
                .all();
    }


}
