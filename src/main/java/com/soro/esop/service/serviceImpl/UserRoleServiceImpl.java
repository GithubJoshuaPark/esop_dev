package com.soro.esop.service.serviceImpl;

import java.util.List;

import com.soro.esop.entiry.UserRole;
import com.soro.esop.entiry.pk.UserRolePk;
import com.soro.esop.repository.UserRoleRepository;
import com.soro.esop.service.UserRoleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {
    
    private final UserRoleRepository userRoleRepository;

    @Override
    public UserRole findById(UserRolePk userRolePk) {
        return userRoleRepository.findById(userRolePk).orElse(null);
    }

    @Override
    public List<UserRole> findAll() {
        List<UserRole> userRoles = userRoleRepository.findAll();
        return userRoles.isEmpty() ? null : userRoles;        
    }

    @Override
    public UserRole save(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    @Override
    public UserRole update(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    @Override
    public void delete(UserRolePk userRolePk) {
        userRoleRepository.deleteById(userRolePk);
    }

    

    
    
}
