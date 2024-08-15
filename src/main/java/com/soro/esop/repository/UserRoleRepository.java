package com.soro.esop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soro.esop.entiry.UserRole;
import com.soro.esop.entiry.pk.UserRolePk;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRolePk> {    
}
