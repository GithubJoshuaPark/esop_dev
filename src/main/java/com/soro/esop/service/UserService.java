package com.soro.esop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.soro.esop.entiry.User;

@Service
public interface UserService {
    User findById(Long id);
    List<User> findAll();
    User save(User user);
    User update(User user);
    void delete(Long id);
}

