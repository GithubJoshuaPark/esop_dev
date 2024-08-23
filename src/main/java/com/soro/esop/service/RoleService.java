package com.soro.esop.service;

import java.util.List;

import com.soro.esop.entity.Role;

public interface RoleService {
    Role findById(Long id);
    List<Role> findAll();
    Role save(Role Role);
    Role update(Role Role);
    void delete(Long id);
}
