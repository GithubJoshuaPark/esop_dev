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

    @Column(name = "cur_bal")
    private Double curBal;         // A: 현재 잔고(read only)

    @Column(name = "cur_stock_price")
    private Double curStockPrice;  // B: 현재 주가(input value)

    @Column(name = "req_qty")
    private Integer reqQty;        // C: 요청 수량(A: curBal / B: curStockPrice)

    @Column(name = "req_amt")
    private Double reqAmt;         // D: 요청 금액 (C: reqQty * B: curStockPrice)

    @Column(name = "after_bal")
    private Double afterBal;       // E: 거래 후 잔고 (A: curBal - D: reqAmt)

    @Column(name = "status")
    private BuyStatus status;      // enum, W: 대기, I: 입력, C: 승인, F: 완료 -> 1,2,3,4

    @PostLoad
    public void postLoad() {
        log.debug("postLoad : {}", this);
    }
}
