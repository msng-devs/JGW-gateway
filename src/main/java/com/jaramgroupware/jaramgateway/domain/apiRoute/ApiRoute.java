package com.jaramgroupware.jaramgateway.domain.apiRoute;

import com.jaramgroupware.jaramgateway.domain.method.Method;
import com.jaramgroupware.jaramgateway.domain.role.Role;
import com.jaramgroupware.jaramgateway.domain.service.Service;
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

    @Column("API_ROUTE_GATEWAY_REFRESH")
    private boolean isGatewayRefresh;

    @Column("API_ROUTE_ONLY_TOKEN")
    private boolean isOnlyToken;

    @Column("API_ROUTE_AUTHORIZATION")
    private boolean isAuthorization;

    @Transient
    private Role role;

    @Transient
    private Service service;

    @Transient
    private Method method;

}
