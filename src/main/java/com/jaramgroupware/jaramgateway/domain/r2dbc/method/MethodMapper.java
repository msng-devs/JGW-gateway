package com.jaramgroupware.jaramgateway.domain.r2dbc.method;

import com.jaramgroupware.jaramgateway.domain.r2dbc.apiRoute.ApiRoute;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class MethodMapper implements BiFunction<Row, RowMetadata, Method> {

    @Override
    public Method apply(Row row, RowMetadata rowMetadata) {
        return Method.builder()
                .id(row.get("METHOD_PK",Integer.class))
                .name(row.get("METHOD_NM",String.class))
                .build();
    }

}
