package com.jaramgroupware.jaramgateway.service;

import com.jaramgroupware.jaramgateway.domain.Error.ErrorInfo;
import com.jaramgroupware.jaramgateway.domain.Error.ErrorInfoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ErrorService {

    @Autowired
    private final ErrorInfoRepository errorRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Transactional(readOnly = true)
    public Mono<ErrorInfo> findErrorByID(Integer id){
        return errorRepository.findByID(id);
    }

    @Transactional(readOnly = true)
    public Mono<ErrorInfo> findErrorByName(String name){
        return errorRepository.findByName(name);
    }
}
