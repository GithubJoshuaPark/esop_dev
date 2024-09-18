package com.soro.esop.service;

import com.soro.esop.entity.DxUser;

import java.util.List;
import java.util.UUID;

public interface DxUserService {
    DxUser findById(String id);
    List<DxUser> findAll();
    DxUser save(DxUser DxUser);
    DxUser update(DxUser DxUser);
    void delete(String id);

}
