package com.jaramgroupware.jaramgateway.service;

import com.jaramgroupware.jaramgateway.domain.member.Member;
import com.jaramgroupware.jaramgateway.domain.member.MemberMapper;
import com.jaramgroupware.jaramgateway.domain.member.MemberRepository;
import com.jaramgroupware.jaramgateway.dto.member.MemberDetailDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class MemberService {

    @Autowired
    private final MemberRepository memberRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Transactional(readOnly = true)
    public Mono<MemberDetailDto> findMemberById(String id){
        return memberRepository.findMemberById(id).map(MemberDetailDto::new);
    }
}
