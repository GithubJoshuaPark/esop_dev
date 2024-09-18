package com.soro.esop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * packageName : com.soro.esop.config
 * fileName    : TransactionConfig
 * author      : soromiso
 * date        : 9/18/24
 * description :
 * ===========================================================
 * DATE                 AUTHOR             NOTE
 * -----------------------------------------------------------
 * 9/18/24             soromiso             new
 */
@Configuration
@RequiredArgsConstructor
public class TransactionConfig {

    private final PlatformTransactionManager transactionManager;

    @Bean
    public TransactionTemplate transactionTemplate() {
        return new TransactionTemplate(transactionManager);
    }
}
