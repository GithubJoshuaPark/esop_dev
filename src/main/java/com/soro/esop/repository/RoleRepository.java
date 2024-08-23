package com.soro.esop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soro.esop.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {    
}
