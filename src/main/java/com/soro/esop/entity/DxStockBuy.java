package com.soro.esop.entity;

import com.soro.esop.entity.enums.BuyStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Slf4j
@Data
@Entity
@Table(name = "dx_stock_buy")
public class DxStockBuy {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    private String id;

    @Column(name = "req_dt")
    private LocalDate reqDt;

    @Column(name = "req_qty")
    private Integer reqQty;

    @Column(name = "req_amt")
    private Double reqAmt;

    @Column(name = "status")
    private BuyStatus status; // enum, W: 대기, I: 입력, C: 승인, F: 완료 -> 1,2,3,4

    @PostLoad
    public void postLoad() {
        log.debug("postLoad : {}", this);
    }
}
