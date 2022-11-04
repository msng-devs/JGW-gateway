package com.jaramgroupware.jaramgateway.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class RefreshController {
    @GetMapping("/refresh")
    public Mono<String> ping(@RequestHeader(name = "user_pk") String user,@RequestHeader(name = "role_pk") String role){
        log.info("Dev (user = {}) refresh gateway!",user);
        return Mono.just("refresh success!");
    }
}
