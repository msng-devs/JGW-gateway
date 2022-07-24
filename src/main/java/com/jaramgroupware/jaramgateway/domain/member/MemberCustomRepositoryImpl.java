package com.jaramgroupware.jaramgateway.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {

    private final DatabaseClient client;
    private final MemberMapper memberMapper;

    @Override
    public Mono<Member> findMemberById(String id) {
        String query = "SELECT * FROM MEMBER\n" +
                "LEFT JOIN ROLE\n" +
                "ON ROLE.ROLE_PK = MEMBER.ROLE_ROLE_PK\n" +
                "LEFT JOIN RANK\n" +
                "ON RANK.RANK_PK = MEMBER.RANK_RANK_PK\n" +
                "LEFT JOIN MAJOR\n" +
                "ON MAJOR.MAJOR_PK = MEMBER.MAJOR_MAJOR_PK\n" +
                "WHERE MEMBER_PK ="+id;

        return client.sql(query)
                .map(memberMapper::apply)
                .first();
    }
}
