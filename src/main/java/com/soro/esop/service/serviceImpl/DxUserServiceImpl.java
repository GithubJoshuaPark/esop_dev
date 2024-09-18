package com.soro.esop.service.serviceImpl;

import com.soro.esop.entity.DxEntity;
import com.soro.esop.entity.DxUser;
import com.soro.esop.repository.DxUserRepository;
import com.soro.esop.service.DxUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class DxUserServiceImpl implements DxUserService {
    private final DxUserRepository dxEntityRepository;

    @Override
    public DxUser findById(String id) {
        return dxEntityRepository.findById(id).orElse(null);
    }

    @Override
    public List<DxUser> findAll() {
        List<DxUser> dxEntitys = dxEntityRepository.findAll();
        return dxEntitys.isEmpty() ? null : dxEntitys;
    }

    @Override
    public DxUser save(DxUser dxEntity) {
        return dxEntityRepository.save(dxEntity);
    }

    @Override
    public DxUser update(DxUser dxEntity) {
        return dxEntityRepository.save(dxEntity);
    }

    @Override
    public void delete(String id) {
        dxEntityRepository.deleteById(id);
    }

    
}
