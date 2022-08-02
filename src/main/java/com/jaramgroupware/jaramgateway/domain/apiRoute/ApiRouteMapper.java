package com.jaramgroupware.jaramgateway.domain.apiRoute;


import com.jaramgroupware.jaramgateway.domain.method.Method;
import com.jaramgroupware.jaramgateway.domain.role.Role;
import com.jaramgroupware.jaramgateway.domain.service.Service;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.springframework.stereotype.Component;


import java.util.function.BiFunction;

@Component
public class ApiRouteMapper implements BiFunction<Row, RowMetadata, ApiRoute> {
    @Override
    public ApiRoute apply(Row row, RowMetadata rowMetadata) {

        return ApiRoute.builder()
                .id(row.get("API_ROUTE_PK",Integer.class))
                .path(row.get("API_ROUTE_PATH",String.class))
                .isGatewayRefresh(row.get("API_ROUTE_GATEWAY_REFRESH",boolean.class))
                .isAddUserInfo(row.get("API_ROUTE_ADD_USER_INFO",boolean.class))
                .isAuthorization(row.get("API_ROUTE_AUTHORIZATION",boolean.class))
                .role(Role.builder()
                        .id(row.get("ROLE_PK",Integer.class))
                        .name(row.get("ROLE_NM",String.class))
                        .build())
                .service(Service.builder()
                        .id(row.get("SERVICE_PK",Integer.class))
                        .name(row.get("SERVICE_NM",String.class))
                        .domain(row.get("SERVICE_DOMAIN",String.class))
                        .index(row.get("SERVICE_INDEX",String.class))
                        .build())
                .method(Method.builder()
                        .id(row.get("METHOD_PK",Integer.class))
                        .name(row.get("METHOD_NM",String.class))
                        .build())
                .build();

    }
}
