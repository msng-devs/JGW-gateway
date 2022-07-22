package com.jaramgroupware.jaramgateway.domain.apiRoute;

import com.jaramgroupware.jaramgateway.domain.method.Method;
import com.jaramgroupware.jaramgateway.domain.role.Role;
import com.jaramgroupware.jaramgateway.domain.service.Service;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name = "API_ROUTE")
public class ApiRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "API_ROUTE_PK")
    private Integer id;

    @Column(name = "API_ROUTE_PATH", nullable = false, unique = true,length = 45)
    private String path;

    @ManyToOne
    @JoinColumn(name = "ROLE_ROLE_PK",nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "SERVICE_SERVICE_PK",nullable = false)
    private Service service;

    @ManyToOne
    @JoinColumn(name = "METHOD_METHOD_PK",nullable = false)
    private Method method;
}
