package com.soro.esop.service.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.soro.esop.entiry.User;
import com.soro.esop.repository.UserRepository;
import com.soro.esop.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        return users.isEmpty() ? null : users;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    
}
