package com.soro.esop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soro.esop.entiry.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {    
}
