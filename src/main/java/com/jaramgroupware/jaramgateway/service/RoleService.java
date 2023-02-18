package com.jaramgroupware.jaramgateway.service;

import com.jaramgroupware.jaramgateway.domain.r2dbc.role.Role;
import com.jaramgroupware.jaramgateway.domain.r2dbc.role.RoleRepository;
import com.jaramgroupware.jaramgateway.dto.role.RoleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoleService {
    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Mono<List<RoleResponseDto>> findAllRole(){
        Flux<Role> roles = roleRepository.findAll();
        return roles.flatMap(
                role -> {
                    return Mono.just(RoleResponseDto.builder()
                            .id(role.getId())
                            .roleName(role.getName())
                            .build()
                    );
                }).collectList();
    }
}
