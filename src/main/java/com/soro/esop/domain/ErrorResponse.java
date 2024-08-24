package com.soro.esop.domain;

import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private List<String> errors;

    public ErrorResponse(String error) {
        this.errors = Collections.singletonList(error);
    }
}
