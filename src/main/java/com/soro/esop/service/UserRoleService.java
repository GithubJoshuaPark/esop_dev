package com.soro.esop.service;

import java.util.List;

import com.soro.esop.entity.UserRole;
import com.soro.esop.entity.pk.UserRolePk;

public interface UserRoleService {
    UserRole findById(UserRolePk userRolePk);
    List<UserRole> findAll();
    List<UserRole> findByUserId(Long userId);
    void deleteByUserId(Long userId);
    UserRole save(UserRole user);
    UserRole update(UserRole user);
    void delete(UserRolePk userRolePk);
}

