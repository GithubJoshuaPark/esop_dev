package com.soro.esop.repository;

import com.soro.esop.entity.DxUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DxUserRepository extends JpaRepository<DxUser, String> {
}
