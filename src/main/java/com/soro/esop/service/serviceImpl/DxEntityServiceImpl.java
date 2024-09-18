package com.soro.esop.service.serviceImpl;

import com.soro.esop.entity.DxEntity;
import com.soro.esop.entity.DxUser;
import com.soro.esop.repository.DxEntityRepository;
import com.soro.esop.service.DxEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class DxEntityServiceImpl implements DxEntityService {
    private final DxEntityRepository dxEntityRepository;

    @Override
    public DxEntity findById(String id) {
        return dxEntityRepository.findById(id).orElse(null);
    }

    @Override
    public List<DxEntity> findAll() {
        List<DxEntity> dxEntitys = dxEntityRepository.findAll();
        return dxEntitys.isEmpty() ? null : dxEntitys;
    }

    @Override
    public DxEntity save(DxEntity dxEntity) {
        return dxEntityRepository.save(dxEntity);
    }

    @Override
    public DxEntity update(DxEntity dxEntity) {
        return dxEntityRepository.save(dxEntity);
    }

    @Override
    public void delete(String id) {
        dxEntityRepository.deleteById(id);
    }

    @Override
    public void createFromDxUser(DxUser dxUser) {
        log.debug("createFromDxUser: {}", dxUser);
        DxEntity dxEntity = new DxEntity();
        updateDxEntityFromDxUser(dxEntity, dxUser);
        log.debug("dxEntity: {}", dxEntity);
        dxEntityRepository.save(dxEntity); // it occurred an error here
    }

    @Override
    public void updateFromDxUser(DxUser dxUser) {
        DxEntity dxEntity = dxEntityRepository.findBySsn(dxUser.getSsn()).orElseThrow();
        updateDxEntityFromDxUser(dxEntity, dxUser);
        dxEntityRepository.save(dxEntity);
    }

    @Override
    public void deleteByDxUser(DxUser dxUser) {
        dxEntityRepository.findBySsn(dxUser.getSsn())
                .ifPresent(dxEntityRepository::delete);
    }

    /**
     * Update DxEntity from DxUser
     * @param dxEntity as target
     * @param dxUser as source
     */
    private void updateDxEntityFromDxUser(DxEntity dxEntity, DxUser dxUser) {
        dxEntity.setName(dxUser.getName());
        dxEntity.setValue(dxUser.getValue());
        dxEntity.setAddress(dxUser.getAddress());
        dxEntity.setPhoneNumber(dxUser.getPhoneNumber());
        dxEntity.setSsn(dxUser.getSsn());
    }
    
}
