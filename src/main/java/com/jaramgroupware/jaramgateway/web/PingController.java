package com.jaramgroupware.jaramgateway.web;

import com.jaramgroupware.jaramgateway.jgwauth.JGWAuthClient;
import com.jaramgroupware.jaramgateway.jgwauth.JGWAuthResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@RestController
public class PingController {

    public final ApplicationEventPublisher applicationEventPublisher;
    public final JGWAuthClient jgwAuthClient;

    @GetMapping("/ping")
    public Mono<String> ping(){
        return Mono.just("PONG");
    }

    @PostMapping("/refresh")
    public Mono<ResponseEntity<String>> refresh(
            @RequestHeader(value = "authorization") String accessToken
    ){
        String tokenString = accessToken.substring(7);
        Mono<JGWAuthResult> jgwAuthResult = jgwAuthClient.authentication(tokenString);
        return jgwAuthResult.map(result->{
            if(result.isValid() && result.getRoleID() >= 5){
                log.info("UID {} start gateway refresh...",result.getUid());
                applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
                return ResponseEntity.ok("finish");
            }
            else {
                return ResponseEntity.status(403).body("you don't have permission");
            }
        });

    }

}
