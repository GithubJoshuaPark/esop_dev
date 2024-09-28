package com.soro.esop.service.serviceImpl;

import com.soro.esop.entity.DxStockBuy;
import com.soro.esop.repository.DxStockBuyRepository;
import com.soro.esop.service.DxStockBuyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DxStockBuyServiceImpl implements DxStockBuyService {
    private final DxStockBuyRepository dxEntityRepository;

    @Override
    public DxStockBuy findById(String id) {
        return dxEntityRepository.findById(id).orElse(null);
    }

    @Override
    public List<DxStockBuy> findAll() {
        List<DxStockBuy> dxEntitys = dxEntityRepository.findAll();
        return dxEntitys.isEmpty() ? null : dxEntitys;
    }

    @Override
    public DxStockBuy save(DxStockBuy dxEntity) {
        return dxEntityRepository.save(dxEntity);
    }

    @Override
    public DxStockBuy update(DxStockBuy dxEntity) {
        return dxEntityRepository.save(dxEntity);
    }

    @Override
    public void delete(String id) {
        dxEntityRepository.deleteById(id);
    }

    
}
