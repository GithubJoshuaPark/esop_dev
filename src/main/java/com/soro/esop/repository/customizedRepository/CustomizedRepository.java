package com.soro.esop.repository.customizedRepository;

import java.util.List;

import com.soro.esop.entiry.User;

public interface CustomizedRepository {
    // // for using JPQL
    // List<User> findByUsernameQuery(String username);

    // // for using native query
    // List<User> findByUsernameNativeQuery(String username);

    // // for using querydsl
    // Iterable<User> findAllOfQueryDsl(Predicate predicate);
    
    List<User> testOfCustomizedRepository(String username);
    List<User> testOfCustomizedRepositoryJDBC(String username);
}
