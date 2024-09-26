package com.soro.esop.domain;

import java.util.Collections;
import java.util.List;
import lombok.Data;

@Data
public class ErrorResponse {
    private String errorCode;
    private String message;
    private List<String> details;

    public ErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
        this.details = Collections.emptyList();
    }

    public ErrorResponse(String errorCode, String message, List<String> details) {
        this.errorCode = errorCode;
        this.message = message;
        this.details = details;
    }

    public ErrorResponse(String errorCode, String message, String detail) {
        this.errorCode = errorCode;
        this.message = message;
        this.details = Collections.singletonList(detail);
    }

    public ErrorResponse(List<String> details) {
        this.errorCode = "ESOP_ERR";
        this.message = "An error occurred";
        this.details = details;
    }

    public ErrorResponse(String detail) {
        this.errorCode = "ESOP_ERR";
        this.message = "An error occurred";
        this.details = Collections.singletonList(detail);
    }

    public ErrorResponse() {
        this.errorCode = "ESOP_ERR";
        this.message = "An error occurred";
        this.details = Collections.emptyList();
    }
}