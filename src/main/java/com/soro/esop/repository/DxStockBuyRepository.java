package com.soro.esop.repository;

import com.soro.esop.entity.DxStockBuy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface DxStockBuyRepository extends JpaRepository<DxStockBuy, String> {

    @Query("select count(d) from DxStockBuy d where d.status = ?1")
    long CntOfEachStatus(Integer status);
}
