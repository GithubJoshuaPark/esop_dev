package com.soro.esop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soro.esop.entity.UserRole;
import com.soro.esop.entity.pk.UserRolePk;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRolePk> {    
    
    // findByUserId
    List<UserRole> findByUserId(Long userId);

    // deleteByUserId
    void deleteByUserId(Long userId);
}
