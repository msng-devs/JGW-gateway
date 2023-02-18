//package com.jaramgroupware.jaramgateway.service;
//
//import com.jaramgroupware.jaramgateway.domain.r2dbc.config.Config;
//import com.jaramgroupware.jaramgateway.domain.r2dbc.config.ConfigRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//
//@RequiredArgsConstructor
//@Service
//public class ConfigService {
//    private final ConfigRepository configRepository;
//
//    public Mono<String> findByKey(String key){
//        return configRepository.findConfigByKey(key).map(Config::getVal);
//    }
//
//
//}
