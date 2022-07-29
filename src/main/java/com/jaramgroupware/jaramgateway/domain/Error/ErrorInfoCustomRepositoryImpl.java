package com.jaramgroupware.jaramgateway.domain.Error;

import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ErrorInfoCustomRepositoryImpl implements ErrorInfoCustomRepository {

    private final DatabaseClient client;
    private final ErrorInfoMapper errorMapper;



    @Override
    public Mono<ErrorInfo> findByID(Integer id) {
        String query = "SELECT * FROM ERROR\n"+
                        "WHERE ERROR_PK = '"+id+"'";

        return client.sql(query)
                .map(errorMapper::apply)
                .one();
    }


    @Override
    public Mono<ErrorInfo> findByName(String name) {
        String query = "SELECT * FROM ERROR\n"+
                "WHERE ERROR_NM = '"+name+"'";

        return client.sql(query)
                .map(errorMapper::apply)
                .one();
    }
}
