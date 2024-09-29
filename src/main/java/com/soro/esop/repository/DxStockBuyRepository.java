package com.soro.esop.repository;

import com.soro.esop.entity.DxStockBuy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface DxStockBuyRepository extends JpaRepository<DxStockBuy, String> {

    @Query("select count(d) from DxStockBuy d where d.status = ?1")
    long CntOfStatus(Integer status);

    //   -- get the one record of the query result
    //    SELECT d.*
    //    FROM dx_stock_buy d
    //    WHERE 1 = 1
    //    AND DATE_FORMAT(d.req_dt, '%Y%m') = REPLACE('2024-09', '-', '')
    //    LIMIT 1
    //    ;
    // Use native query to execute MySQL specific query
    @Query(value = "SELECT * FROM dx_stock_buy d WHERE DATE_FORMAT(d.req_dt, '%Y%m') = REPLACE(?1, '-', '') LIMIT 1", nativeQuery = true)
    DxStockBuy findOneRowByReqDt(String reqDt);

}
