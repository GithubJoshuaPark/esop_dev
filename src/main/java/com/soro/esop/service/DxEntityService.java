package com.soro.esop.service;

import com.soro.esop.entity.DxEntity;

import java.util.List;

public interface DxEntityService {
    DxEntity findById(Long id);
    List<DxEntity> findAll();
    DxEntity save(DxEntity DxEntity);
    DxEntity update(DxEntity DxEntity);
    void delete(Long id);
}
