package com.jaramgroupware.jaramgateway.service;

import com.jaramgroupware.jaramgateway.domain.rank.RankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RankService {

    @Autowired
    private final RankRepository rankRepository;

    @Transactional(readOnly = true)
    public String findById(Integer id){
        return rankRepository.findRankById(id).getName();
    }
}
