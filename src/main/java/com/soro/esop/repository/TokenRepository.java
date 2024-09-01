package com.soro.esop.repository;

import com.soro.esop.entity.Token;
import com.soro.esop.repository.queryDsl.TokenDslRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long>, TokenDslRepository {
    Token save(Token token);
}
