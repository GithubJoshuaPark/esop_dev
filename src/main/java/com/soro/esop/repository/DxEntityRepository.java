package com.soro.esop.repository;

import com.soro.esop.entity.DxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DxEntityRepository extends JpaRepository<DxEntity, String> {
    Optional<DxEntity> findBySsn(String ssn);
    Optional<DxEntity> findById(String id);
}
