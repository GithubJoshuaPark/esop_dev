package com.soro.esop.repository.queryDsl;

import com.soro.esop.entity.Token;

import java.util.List;
import java.util.Optional;

/**
 * packageName : com.soro.esop.repository.queryDsl
 * fileName    : TokenRepository
 * author      : soromiso
 * date        : 9/1/24
 * description :
 * ===========================================================
 * DATE                 AUTHOR             NOTE
 * -----------------------------------------------------------
 * 9/1/24             soromiso             new
 */
public interface TokenDslRepository {
    Optional<Token> findByToken(String token);
    List<Token> findAllValidTokensByUserName(String userName);
    long updateAllByUserName(String userName, boolean expired, boolean revoked);
}
