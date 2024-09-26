package com.soro.esop.esopException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

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
    private String errorCode;
    private List<String> details;

    public EsopException(String message) {
        super(message);
        this.errorCode = "ESOP_ERR";
    }

    public EsopException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public EsopException(String errorCode, String message, List<String> details) {
        super(message);
        this.errorCode = errorCode;
        this.details = details;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public List<String> getDetails() {
        return details;
    }
}
