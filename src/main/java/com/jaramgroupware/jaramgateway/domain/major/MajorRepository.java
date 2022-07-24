package com.jaramgroupware.jaramgateway.domain.major;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MajorRepository extends ReactiveCrudRepository<Major,Integer> {
    Major findMajorById (Integer id);
}
