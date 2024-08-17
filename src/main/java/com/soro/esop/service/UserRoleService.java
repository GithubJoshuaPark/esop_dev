package com.soro.esop.service;

import java.util.List;

import com.soro.esop.entiry.UserRole;
import com.soro.esop.entiry.pk.UserRolePk;

public interface UserRoleService {
    UserRole findById(UserRolePk id);
    List<UserRole> findAll();
    UserRole save(UserRole user);
    UserRole update(UserRole user);
    void delete(UserRolePk id);
}

