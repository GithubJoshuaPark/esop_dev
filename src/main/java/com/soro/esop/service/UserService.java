package com.soro.esop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.soro.esop.entiry.User;
import com.soro.esop.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
