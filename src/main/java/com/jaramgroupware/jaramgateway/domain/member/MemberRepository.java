package com.jaramgroupware.jaramgateway.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member,String> {
    Member findMemberById(String id);
    List<Member> findAllBy();
}
