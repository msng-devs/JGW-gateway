package com.jaramgroupware.jaramgateway.domain.r2dbc.service;

import com.jaramgroupware.jaramgateway.domain.r2dbc.role.Role;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class ServiceInfoMapper implements BiFunction<Row, RowMetadata, ServiceInfo> {
    @Override
    public ServiceInfo apply(Row row, RowMetadata rowMetadata) {
        return ServiceInfo.builder()
                .id(row.get("SERVICE_PK",Integer.class))
                .name(row.get("SERVICE_NM",String.class))
                .domain(row.get("SERVICE_DOMAIN",String.class))
                .index(row.get("SERVICE_INDEX",String.class))
                .build();
    }
}
