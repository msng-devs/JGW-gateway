package com.jaramgroupware.jaramgateway.service;

import com.jaramgroupware.jaramgateway.domain.apiRoute.ApiRoute;
import com.jaramgroupware.jaramgateway.domain.apiRoute.ApiRouteRepository;
import com.jaramgroupware.jaramgateway.dto.route.RouteResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
public class ApiRouteService {

    @Autowired
    private final ApiRouteRepository routeRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());



    /**
     * find route by route's pk
     * @param id route pk
     * @return single route's info
     */
    @Transactional(readOnly = true)
    public RouteResponseDto findRouteByID(Integer id){
        return new RouteResponseDto(routeRepository.findRouteById(id));
    }

    /**
     * find all route
     * @return mutliple route's info
     */
    @Transactional(readOnly = true)
    public Flux<ApiRoute> findAllRoute(){
        return routeRepository.findAllBy();
    }
}
