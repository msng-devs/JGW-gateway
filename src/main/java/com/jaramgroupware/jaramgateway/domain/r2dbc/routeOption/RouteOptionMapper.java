package com.jaramgroupware.jaramgateway.domain.r2dbc.routeOption;

import com.jaramgroupware.jaramgateway.domain.r2dbc.role.Role;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class RouteOptionMapper implements BiFunction<Row, RowMetadata, RouteOption> {
    @Override
    public RouteOption apply(Row row, RowMetadata rowMetadata) {
        return RouteOption.builder()
                .id(row.get("ROUTE_OPTION_PK",Integer.class))
                .name(row.get("ROUTE_OPTION_NM",String.class))
                .build();
    }
}
