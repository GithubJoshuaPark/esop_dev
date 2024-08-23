package com.soro.esop.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.soro.esop.entity.User;

public interface UserService {
    User findById(Long id);
    Page<User> findAll(Pageable pageable);
    List<User> findAll();
    List<User> findByUsernameQuery(String username);
    List<User> findByUsernameNativeQuery(String username);
    //Iterable<User> findAllOfQueryDsl(Predicate predicate); // for using querydsl
    List<User> testOfCustomizedRepository(String username);
    List<User> testOfCustomizedRepositoryJDBC(String username);
    User save(User user);
    User update(User user);
    void delete(Long id);
    User findByUsername(String username);
}

