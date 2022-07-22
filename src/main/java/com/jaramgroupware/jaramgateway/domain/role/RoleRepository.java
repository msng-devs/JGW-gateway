package com.jaramgroupware.jaramgateway.domain.role;

import com.jaramgroupware.jaramgateway.domain.major.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findRoleById (Integer id);
}
