package com.soro.esop.repository;

import java.util.List;

import com.querydsl.core.types.Predicate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.querydsl.QuerydslPredicateExecutor;

//import com.querydsl.core.types.Predicate;
import com.soro.esop.entity.User;
import com.soro.esop.repository.emJdbc.CustomizedRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserRepository extends JpaRepository<User, Long>,
                                         QuerydslPredicateExecutor<User>,
                                        CustomizedRepository
{
    User findByUsername(String username);

    // JPQL
    @Query("SELECT u FROM User u WHERE u.username like %?1%")
    List<User> findByUsernameQuery(String username);

    // native query
    // @Query(value = "SELECT * FROM user WHERE username like %?1%", nativeQuery = true) // for MariaDB
    @Query(value = "SELECT * FROM user WHERE username LIKE CONCAT('%', ?1, '%')", nativeQuery = true) // for MySQL
    List<User> findByUsernameNativeQuery(String username);

    // querydsl is available owing to QuerydslPredicateExecutor<User>
        // querydsl is not used in this project
        // ref: https://docs.spring.io/spring-data/jpa/reference/repositories/core-extensions.html#core.web.type-safe
    Iterable<User> findAll(Predicate predicate);

    // available of testOfCustomizedRepository() owing to CustomizedRepository
    // avavilable of testOfCustomizedRepositoryJDBC() owing to CustomizedRepository


}
