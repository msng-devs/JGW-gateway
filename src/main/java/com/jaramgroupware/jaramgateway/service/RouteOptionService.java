package com.jaramgroupware.jaramgateway.service;

import com.jaramgroupware.jaramgateway.domain.r2dbc.routeOption.RouteOption;
import com.jaramgroupware.jaramgateway.domain.r2dbc.routeOption.RouteOptionRepository;
import com.jaramgroupware.jaramgateway.dto.routeOption.RouteOptionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RouteOptionService {
    private final RouteOptionRepository routeOptionRepository;

    @Transactional(readOnly = true)
    public Mono<List<RouteOptionResponseDto>> findAllOption(){
        Flux<RouteOption> routeOptions = routeOptionRepository.findAllBy();

        return routeOptions.flatMap(
                routeOption -> {
                    return Mono.just(RouteOptionResponseDto
                            .builder()
                            .id(routeOption.getId())
                            .optionName(routeOption.getName())
                            .build());
                }).collectList();
    }
}
