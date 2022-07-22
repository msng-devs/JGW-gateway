package com.jaramgroupware.jaramgateway.service;

import com.jaramgroupware.jaramgateway.domain.major.MajorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MajorService {

    @Autowired
    private final MajorRepository majorRepository;

    @Transactional(readOnly = true)
    public String findById(Integer id){
        return majorRepository.findMajorById(id).getName();
    }
}
