package com.soro.esop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.soro.esop.entiry.User;
import com.soro.esop.repository.customizedRepository.CustomizedRepository;

public interface UserRepository extends JpaRepository<User, Long>, 
                                        //QuerydslPredicateExecutor<User>,
                                        CustomizedRepository //  
{
    User findByUsername(String username);

    // JPQL
    @Query("SELECT u FROM User u WHERE u.username like %?1%")
    List<User> findByUsernameQuery(String username);

    // native query
    @Query(value = "SELECT * FROM user WHERE username like %?1%", nativeQuery = true)
    List<User> findByUsernameNativeQuery(String username);

    // querydsl is available owing to QuerydslPredicateExecutor<User>
        // querydsl is not used in this project
        // ref: https://docs.spring.io/spring-data/jpa/reference/repositories/core-extensions.html#core.web.type-safe
        // Iterable<T> findAll(Predicate predicate);

    // available of testOfCustomizedRepository() owing to CustomizedRepository
    // avavilable of testOfCustomizedRepositoryJDBC() owing to CustomizedRepository

    
}
