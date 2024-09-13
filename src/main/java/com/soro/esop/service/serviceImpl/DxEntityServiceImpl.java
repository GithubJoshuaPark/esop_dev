package com.soro.esop.service.serviceImpl;

import com.soro.esop.entity.DxEntity;
import com.soro.esop.repository.DxEntityRepository;
import com.soro.esop.service.DxEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DxEntityServiceImpl implements DxEntityService {
    private final DxEntityRepository dxEntityRepository;

    @Override
    public DxEntity findById(Long id) {
        return dxEntityRepository.findById(id).orElse(null);
    }

    @Override
    public List<DxEntity> findAll() {
        List<DxEntity> dxEntitys = dxEntityRepository.findAll();
        return dxEntitys.isEmpty() ? null : dxEntitys;
    }

    @Override
    public DxEntity save(DxEntity DxEntity) {
        return dxEntityRepository.save(DxEntity);
    }

    @Override
    public DxEntity update(DxEntity DxEntity) {
        return dxEntityRepository.save(DxEntity);
    }

    @Override
    public void delete(Long id) {
        dxEntityRepository.deleteById(id);
    }
    
}
