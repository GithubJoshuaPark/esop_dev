package com.soro.esop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.soro.esop.entiry.Role;

@Service
public interface RoleService {
    Role findById(Long id);
    List<Role> findAll();
    Role save(Role Role);
    Role update(Role Role);
    void delete(Long id);
}
