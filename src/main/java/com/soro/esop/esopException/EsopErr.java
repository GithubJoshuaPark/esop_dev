package com.soro.esop.esopException;

/**
 * packageName : com.soro.esop.exception
 * fileName    : EsopErr
 * author      : soromiso
 * date        : 9/27/24
 * description :
 * ===========================================================
 * DATE                 AUTHOR             NOTE
 * -----------------------------------------------------------
 * 9/27/24             soromiso             new
 */
public class EsopErr {

    // Member(조합원) Error Messages
    public static final String HIS_NOT_FOUND = "기 등록된 거래 이력이 없습니다";
    public static final String INVALID_INPUT = "잘못된 입력 값입니다";
    public static final String NOT_FOUND = "조합원을 찾을 수 없습니다";

    // Buy(매수) Error Messages

    // Sale(매도) Error Messages

    // Tax(세무) Error Messages

    // Loan(대여금) Error Messages

    // Contribution(출연금) Error Messages

    // Etc Error Messages
    public static final String INSUFFICIENT_BALANCE = "잔액이 부족합니다";
    public static final String INVALID_TRANSACTION = "유효하지 않은 거래입니다";
}
