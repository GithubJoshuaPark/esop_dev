package com.soro.esop.service.serviceImpl;

import com.soro.esop.entity.DxEntity;
import com.soro.esop.entity.DxUser;
import com.soro.esop.repository.DxEntityRepository;
import com.soro.esop.service.DxEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createFromDxUser(DxUser dxUser) {
        log.debug("createFromDxUser: {}", dxUser);
        DxEntity dxEntity = new DxEntity();
        setDxEntityWithDxUser(dxEntity, dxUser);
        log.debug("dxEntity: {}", dxEntity);
        dxEntityRepository.save(dxEntity); // it occurred an error here
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateFromDxUser(DxUser dxUser) {
        log.debug("updateFromDxUser: {}", dxUser);

        Optional<DxEntity> dxEntity = dxEntityRepository.findBySsn(dxUser.getSsn());
        if (dxEntity.isEmpty()) {
            log.error("DxEntity not found by ssn: {}", dxUser.getSsn());
        }
        else
        {
            log.debug("DxEntity found by ssn: {}", dxUser.getSsn());
            dxEntity.ifPresent(entity -> {
                setDxEntityWithDxUser(entity, dxUser);
                dxEntityRepository.save(entity);
            });
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteByDxUser(DxUser dxUser) {
        dxEntityRepository.findBySsn(dxUser.getSsn())
                .ifPresent(dxEntityRepository::delete);
    }

    /**
     * Update DxEntity from DxUser
     * @param dxEntity as target
     * @param dxUser as source
     */
    private void setDxEntityWithDxUser(DxEntity dxEntity, DxUser dxUser) {
        dxEntity.setName(dxUser.getName());
        dxEntity.setValue(dxUser.getValue());
        dxEntity.setAddress(dxUser.getAddress());
        dxEntity.setPhoneNumber(dxUser.getPhoneNumber());
        dxEntity.setSsn(dxUser.getSsn());
        dxEntity.setDescription(dxUser.getDescription());
        dxEntity.setFromDate(LocalDate.now());
        dxEntity.setToDate(LocalDate.now().plusYears(1));
    }
    
}
