package com.soro.esop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soro.esop.entiry.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    
}
