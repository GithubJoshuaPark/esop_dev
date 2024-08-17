package com.soro.esop.service;

import java.util.List;

import com.soro.esop.entiry.User;

public interface UserService {
    User findById(Long id);
    List<User> findAll();
    User save(User user);
    User update(User user);
    void delete(Long id);
}

