package com.jaramgroupware.jaramgateway.domain.method;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MethodRepository extends JpaRepository<Method,Integer> {
}
