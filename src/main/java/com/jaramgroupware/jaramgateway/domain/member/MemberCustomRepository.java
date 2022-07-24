package com.jaramgroupware.jaramgateway.domain.member;

import reactor.core.publisher.Mono;

public interface MemberCustomRepository{
    Mono<Member> findMemberById(String id);
}
