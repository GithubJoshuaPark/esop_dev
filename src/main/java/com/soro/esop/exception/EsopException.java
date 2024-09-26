package com.soro.esop.exception;

/**
 * packageName : com.soro.esop.exception
 * fileName    : EsopException
 * author      : soromiso
 * date        : 9/27/24
 * description :
 * ===========================================================
 * DATE                 AUTHOR             NOTE
 * -----------------------------------------------------------
 * 9/27/24             soromiso             new
 */
public class EsopException extends RuntimeException{
    private final String errorCode;

    public EsopException(String message) {
        super(message);
        this.errorCode = "ESOP_ERR";
    }

    public EsopException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
