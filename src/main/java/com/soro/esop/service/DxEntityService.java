package com.soro.esop.service;

import com.soro.esop.entity.DxEntity;
import com.soro.esop.entity.DxUser;

import java.util.List;
import java.util.UUID;

public interface DxEntityService {
    DxEntity findById(String id);
    List<DxEntity> findAll();
    DxEntity save(DxEntity DxEntity);
    DxEntity update(DxEntity DxEntity);
    void delete(String id);

    // New methods
    void createFromDxUser(DxUser dxUser);
    void updateFromDxUser(DxUser dxUser);
    void deleteByDxUser(DxUser dxUser);
}
