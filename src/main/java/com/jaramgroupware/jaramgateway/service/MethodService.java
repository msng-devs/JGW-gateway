package com.jaramgroupware.jaramgateway.service;

import com.jaramgroupware.jaramgateway.domain.r2dbc.method.Method;
import com.jaramgroupware.jaramgateway.domain.r2dbc.method.MethodRepository;
import com.jaramgroupware.jaramgateway.dto.method.MethodResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MethodService {
    private final MethodRepository methodRepository;

    @Transactional(readOnly = true)
    public Mono<List<MethodResponseDto>> findAllMethod(){
        Flux<Method> methods = methodRepository.findAllBy();

        return methods.flatMap(
                method -> {
                    return Mono.just(MethodResponseDto.builder()
                                .id(method.getId())
                            .methodName(method.getName())
                            .build());
                }).collectList();
    }
}
