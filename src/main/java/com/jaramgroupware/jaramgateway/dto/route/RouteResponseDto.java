package com.jaramgroupware.jaramgateway.dto.route;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.jaramgroupware.jaramgateway.domain.r2dbc.apiRoute.ApiRoute;
import lombok.*;

@Builder
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@ToString
@Getter
@Setter
@AllArgsConstructor
public class RouteResponseDto {

    private Integer id;
    private String path;
    private String roleName;
    private String optionName;
    private String methodName;


    public RouteResponseDto(ApiRoute route){
        this.id = route.getId();
        this.path = route.getPath();
        this.roleName = (route.getRole() != null)? route.getRole().getName() : null;
        this.optionName = route.getRouteOption().getName();
        this.methodName = route.getMethod().getName();
    }
}
