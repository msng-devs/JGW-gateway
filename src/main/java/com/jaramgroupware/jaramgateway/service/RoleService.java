package com.jaramgroupware.jaramgateway.service;

import com.jaramgroupware.jaramgateway.domain.config.ConfigRepository;
import com.jaramgroupware.jaramgateway.domain.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RoleService {

    @Autowired
    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public String findById(Integer id){
        return roleRepository.findRoleById(id).getName();
    }
}
