package com.jaramgroupware.jaramgateway.domain.r2dbc.role;

import com.jaramgroupware.jaramgateway.domain.r2dbc.method.Method;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class RoleMapper implements BiFunction<Row, RowMetadata, Role> {

    @Override
    public Role apply(Row row, RowMetadata rowMetadata) {
        return Role.builder()
                .id(row.get("ROLE_PK",Integer.class))
                .name(row.get("ROLE_NM",String.class))
                .build();
    }

}
