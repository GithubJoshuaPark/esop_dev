package com.soro.esop.exception;

import com.soro.esop.domain.ErrorResponse;
import com.soro.esop.domain.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalTime.now;

/**
 * packageName : com.soro.esop.exception
 * fileName    : GlobalHandleException
 * author      : soromiso
 * date        : 9/27/24
 * description :
 * ===========================================================
 * DATE                 AUTHOR             NOTE
 * -----------------------------------------------------------
 * 9/27/24             soromiso             new
 */
@Slf4j
@RestControllerAdvice // @ControllerAdvice + @ResponseBody
public class GlobalHandleException extends ResponseEntityExceptionHandler implements ErrorController {

    private String errMsg_ = "";

    @ExceptionHandler(EsopException.class)
    public ResponseEntity<ErrorResponse> handleEsopException(EsopException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             Object body,
                                                             HttpHeaders headers,
                                                             HttpStatusCode statusCode,
                                                             WebRequest request) {
        log.error("^^: handleExceptionInternal: {}", ex.getMessage());
        errMsg_ = String.format("handleExceptionInternal: %s", ex.getMessage());
        return new ResponseEntity<>(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .reason(errMsg_)
                        .developerMessage(ex.getMessage())
                        .status(HttpStatus.resolve(statusCode.value()))
                        .statusCode(statusCode.value())
                        .build(), statusCode);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode statusCode,
                                                                  WebRequest request) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        String fieldMessage = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        log.error("^^: MethodArgumentNotValidException: {}", fieldMessage);
        errMsg_ = String.format("MethodArgumentNotValidException: %s", fieldMessage);

        return new ResponseEntity<>(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .reason(errMsg_)
                        .developerMessage(ex.getMessage())
                        .status(HttpStatus.resolve(statusCode.value()))
                        .statusCode(statusCode.value())
                        .build(), statusCode);
    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> handleException(Exception ex) {
        log.error("^^: Exception: {}", ex.getMessage());
        errMsg_ = String.format("Exception: %s", ex.getMessage());
        return new ResponseEntity<>(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .reason(errMsg_)
                        .developerMessage(ex.getMessage())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
