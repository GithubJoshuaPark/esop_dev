package com.soro.esop.domain;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

import java.io.Serializable;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@JsonInclude(NON_DEFAULT)
public class HttpResponse implements Serializable {
    private static final long serialVersionUID = 1L;

protected String timeStamp;
    protected HttpStatus status;
    protected int statusCode;
    protected String reason;
    protected String message;
    protected String developerMessage;
    protected Map<?, ?> data;
}
