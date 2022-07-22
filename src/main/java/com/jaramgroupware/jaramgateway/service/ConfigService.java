package com.jaramgroupware.jaramgateway.service;

import com.jaramgroupware.jaramgateway.domain.config.ConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ConfigService {

    @Autowired
    private final ConfigRepository configRepository;

    @Transactional(readOnly = true)
    public String findConfig(String name){
        return configRepository.findConfigByName(name).getVal();
    }
}
