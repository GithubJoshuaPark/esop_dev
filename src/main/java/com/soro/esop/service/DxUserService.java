package com.soro.esop.service;

import com.soro.esop.entity.DxUser;

import java.util.List;

public interface DxUserService {
    DxUser findById(Long id);
    List<DxUser> findAll();
    DxUser save(DxUser DxUser);
    DxUser update(DxUser DxUser);
    void delete(Long id);
}
