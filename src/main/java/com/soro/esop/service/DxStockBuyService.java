package com.soro.esop.service;

import com.soro.esop.entity.DxStockBuy;

import java.util.List;

public interface DxStockBuyService {
    DxStockBuy findById(String id);
    List<DxStockBuy> findAll();
    DxStockBuy save(DxStockBuy DxStockBuy);
    DxStockBuy update(DxStockBuy DxStockBuy);
    void delete(String id);

}
