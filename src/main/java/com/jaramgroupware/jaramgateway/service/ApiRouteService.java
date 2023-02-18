package com.jaramgroupware.jaramgateway.service;

import com.jaramgroupware.jaramgateway.domain.r2dbc.apiRoute.ApiRoute;
import com.jaramgroupware.jaramgateway.domain.r2dbc.apiRoute.ApiRouteRepository;
import com.jaramgroupware.jaramgateway.dto.route.RouteResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ApiRouteService {

    @Autowired
    private final ApiRouteRepository routeRepository;


    /**
     * find all route
     * @return mutliple route's info
     */
    @Transactional(readOnly = true)
    public Flux<ApiRoute> findAllRoute(){
        return routeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Mono<List<RouteResponseDto>> findAllRouteByServiceId(Integer serviceId){
        Flux<ApiRoute> routes = routeRepository.findAllByServiceId(serviceId);
        return routes.flatMap(
                route ->{
                    return Mono.just(
                            RouteResponseDto.builder()
                            .id(route.getId())
                            .optionName(route.getRouteOption().getName())
                            .methodName(route.getMethod().getName())
                            .path(route.getPath())
                            .roleName((route.getRole() != null)? route.getRole().getName() : null)
                            .build()
                    );
                }
        ).collectList();
    }
}
