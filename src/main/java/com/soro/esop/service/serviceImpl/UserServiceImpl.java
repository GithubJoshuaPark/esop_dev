package com.soro.esop.service.serviceImpl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Page<User> findAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.isEmpty() ? null : users;
    }

    @Override
    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        return users.isEmpty() ? null : users;
    }

    // for using JPQL
    @Override
    public List<User> findByUsernameQuery(String username) {
        return userRepository.findByUsernameQuery(username);
    }

    // for using native query
    @Override
    public List<User> findByUsernameNativeQuery(String username) {
        return userRepository.findByUsernameNativeQuery(username);
    }

    // for using querydsl
    // @Override
    // public Iterable<User> findAllOfQueryDsl(Predicate predicate) {
    //     return userRepository.findAll(predicate);
    // }

    // for using CustomizedRepository
    @Override
    public List<User> testOfCustomizedRepository(String username) {
        return userRepository.testOfCustomizedRepository(username);
    }

    // for using CustomizedRepositoryJDBC
    @Override
    public List<User> testOfCustomizedRepositoryJDBC(String username) {
        return userRepository.testOfCustomizedRepositoryJDBC(username);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    
}
