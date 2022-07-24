package com.jaramgroupware.jaramgateway.domain.rank;


import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankRepository extends ReactiveCrudRepository<Rank,Integer> {
    Rank findRankById (Integer id);
}
