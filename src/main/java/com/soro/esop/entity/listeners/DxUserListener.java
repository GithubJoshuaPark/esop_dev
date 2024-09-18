package com.soro.esop.entity.listeners;

import com.soro.esop.entity.DxUser;
import com.soro.esop.service.DxEntityService;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    @Autowired
    public void setDxEntityService(DxEntityService dxEntityService) {
        DxUserListener.dxEntityService = dxEntityService;
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
