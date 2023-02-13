package com.jaramgroupware.jaramgateway.domain.r2dbc.apiRoute;

import com.jaramgroupware.jaramgateway.domain.r2dbc.method.Method;
import com.jaramgroupware.jaramgateway.domain.r2dbc.role.Role;
import com.jaramgroupware.jaramgateway.domain.r2dbc.routeOption.RouteOption;
import com.jaramgroupware.jaramgateway.domain.r2dbc.service.ServiceInfo;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "API_ROUTE")
public class ApiRoute {

    @Id
    @Column("API_ROUTE_PK")
    private Integer id;

    @Column("API_ROUTE_PATH")
    private String path;

    @Transient
    private Role role;

    @Transient
    private ServiceInfo service;

    @Transient
    private Method method;

    @Transient
    private RouteOption routeOption;

}
