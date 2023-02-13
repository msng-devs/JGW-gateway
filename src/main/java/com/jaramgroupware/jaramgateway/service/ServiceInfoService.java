package com.jaramgroupware.jaramgateway.service;

import com.jaramgroupware.jaramgateway.domain.r2dbc.service.ServiceInfo;
import com.jaramgroupware.jaramgateway.domain.r2dbc.service.ServiceInfoRepository;
import com.jaramgroupware.jaramgateway.dto.service.ServiceInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ServiceInfoService {
    private final ServiceInfoRepository serviceRepository;

    @Transactional(readOnly = true)
    public Mono<List<ServiceInfoResponseDto>> findAllService(){
        Flux<ServiceInfo> result = serviceRepository.findAllBy();

        return result.flatMap(serviceInfo
                ->{
            return Mono.just(ServiceInfoResponseDto.builder()
                    .id(serviceInfo.getId())
                    .index(serviceInfo.getIndex())
                    .domain(serviceInfo.getDomain())
                    .name(serviceInfo.getName())
                    .build());
        }).collectList();
    }
}
