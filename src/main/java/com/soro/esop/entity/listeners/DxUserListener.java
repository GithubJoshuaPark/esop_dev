package com.soro.esop.entity.listeners;

import com.soro.esop.entity.DxEntity;
import com.soro.esop.entity.DxUser;
import com.soro.esop.repository.DxEntityRepository;
import com.soro.esop.service.DxEntityService;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * packageName : com.soro.esop.listener
 * fileName    : DxUserListener
 * author      : soromiso
 * date        : 9/18/24
 * description :
 * ===========================================================
 * DATE                 AUTHOR             NOTE
 * -----------------------------------------------------------
 * 9/18/24             soromiso             new
 */
@Slf4j
@Component
public class DxUserListener {

    private static DxEntityService dxEntityService;
    private static TransactionTemplate transactionTemplate;

    @Autowired
    public void setDxEntityService(DxEntityService dxEntityService) {
        DxUserListener.dxEntityService = dxEntityService;
    }

    @Autowired
    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        DxUserListener.transactionTemplate = transactionTemplate;
    }

    @PostPersist
    public void postPersist(DxUser dxUser) {
        log.info("postPersist: {}", dxUser);
        dxEntityService.createFromDxUser(dxUser);
    }

    @PostUpdate
    public void postUpdate(DxUser dxUser) {
        log.info("postUpdate: {}", dxUser);
        dxEntityService.updateFromDxUser(dxUser);
    }

    @PostRemove
    public void postRemove(DxUser dxUser) {
        log.info("postRemove: {}", dxUser);
        dxEntityService.deleteByDxUser(dxUser);
    }

}
