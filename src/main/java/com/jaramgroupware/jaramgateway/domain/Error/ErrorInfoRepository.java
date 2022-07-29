package com.jaramgroupware.jaramgateway.domain.Error;



import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ErrorInfoRepository extends ReactiveCrudRepository<ErrorInfo,Integer> , ErrorInfoCustomRepository {
}
