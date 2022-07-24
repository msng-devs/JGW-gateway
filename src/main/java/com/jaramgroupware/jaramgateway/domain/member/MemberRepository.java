package com.jaramgroupware.jaramgateway.domain.member;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.BiFunction;

@Repository
public interface MemberRepository extends ReactiveCrudRepository<Member,String>,MemberCustomRepository{

}
