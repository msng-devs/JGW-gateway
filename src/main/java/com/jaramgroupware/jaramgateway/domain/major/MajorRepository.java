package com.jaramgroupware.jaramgateway.domain.major;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MajorRepository extends JpaRepository<Major,Integer> {
    Major findMajorById (Integer id);
}
