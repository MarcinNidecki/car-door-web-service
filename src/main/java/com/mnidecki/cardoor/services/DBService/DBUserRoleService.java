package com.mnidecki.cardoor.services.DBService;

import com.mnidecki.cardoor.domain.UserRole;
import com.mnidecki.cardoor.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBUserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    public UserRole getUserRoleByRoleName(String roleName){
        return userRoleRepository.getUserRoleByRoleName(roleName);
    }
}
