package com.jaramgroupware.jaramgateway.service;

import com.jaramgroupware.jaramgateway.domain.member.MemberRepository;
import com.jaramgroupware.jaramgateway.dto.member.MemberDetailDto;
import com.jaramgroupware.jaramgateway.dto.member.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberService {

    @Autowired
    private final MemberRepository memberRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * find member by member's uid(MEMBER_PK)
     * @param id target member's uid
     * @return single member's info
     */
    @Transactional(readOnly = true)
    public MemberResponseDto findMemberByID(String id){
        return new MemberResponseDto(memberRepository.findMemberById(id));
    }

    /**
     * find all member
     * @return mutliple memeber's info
     */
    @Transactional(readOnly = true)
    public List<MemberResponseDto> findAllMember(){
        return memberRepository.findAllBy().stream()
                .map(MemberResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MemberDetailDto memberDetail(String id){
        return new MemberDetailDto(memberRepository.findMemberById(id));
    }
}
