package com.jaramgroupware.jaramgateway.domain.r2dbc.service;

import com.jaramgroupware.jaramgateway.domain.r2dbc.role.RoleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ServiceInfoCustomRepositoryImpl implements ServiceInfoCustomRepository {

    private final DatabaseClient client;
    private final ServiceInfoMapper serviceInfoMapper;

    @Override
    public Flux<ServiceInfo> findAllBy() {
        String query = "SELECT * FROM SERVICE";

        return client.sql(query)
                .map(serviceInfoMapper)
                .all();
    }

    @Override
    public Mono<ServiceInfo> findServiceById(Integer id) {
        String query = "SELECT * FROM SERVICE WHERE SERVICE_PK = ?";
        return client.sql(query)
                .bind(0,id)
                .map(serviceInfoMapper)
                .one();
    }
}
