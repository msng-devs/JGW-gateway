package com.jaramgroupware.jaramgateway.domain.r2dbc.method;

import com.jaramgroupware.jaramgateway.domain.r2dbc.apiRoute.ApiRouteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class MethodCustomRepositoryImpl implements MethodCustomRepository {

    private final DatabaseClient client;
    private final MethodMapper methodMapper;

    @Override
    public Flux<Method> findAllBy() {
        String query = "SELECT * FROM METHOD";

        return client.sql(query)
                .map(methodMapper)
                .all();
    }
}
