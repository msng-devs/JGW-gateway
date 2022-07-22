package com.jaramgroupware.jaramgateway.dto.route;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jaramgroupware.jaramgateway.domain.apiRoute.ApiRoute;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;



@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@ToString
@Getter
@NoArgsConstructor
public class RouteResponseDto {

    private Integer id;
    private String path;
    private String role;
    private String  service;
    private String  method;

    @Builder
    public RouteResponseDto(Integer id, String path, String  role, String  service, String  method) {
        this.id = id;
        this.path = path;
        this.role = role;
        this.service = service;
        this.method = method;
    }

    public RouteResponseDto(ApiRoute route){
        this.id = route.getId();
        this.path = route.getPath();
        this.role = route.getRole().getName();
        this.service = route.getService().getName();
        this.method = route.getMethod().getName();
    }
}
