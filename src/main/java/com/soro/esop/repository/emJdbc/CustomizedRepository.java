package com.soro.esop.repository.emJdbc;

import java.util.List;

import com.soro.esop.entity.User;

public interface CustomizedRepository {
    List<User> testOfCustomizedRepository(String username);
    List<User> testOfCustomizedRepositoryJDBC(String username);
}
