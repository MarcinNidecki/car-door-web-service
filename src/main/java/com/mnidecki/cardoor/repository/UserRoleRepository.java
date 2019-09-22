package com.mnidecki.cardoor.repository;

import com.mnidecki.cardoor.domain.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Integer> {
    UserRole getUserRoleByRoleName(String roleName);
}
