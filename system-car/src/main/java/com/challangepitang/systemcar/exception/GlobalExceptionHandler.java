package com.challangepitang.systemcar.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            UnauthorizedException.class,
            InvalidSessionException.class,
            LicensePlateAlreadyExistsException.class,
            InvalidFieldsException.class,
            MissingFieldsException.class,
            InvalidLoginOrPasswordException.class,
            EmailAlreadyExistsException.class,
            LoginAlreadyExistsException.class
    })
    public ResponseEntity<Object> handleCustomExceptions(RuntimeException ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex instanceof UnauthorizedException || ex instanceof InvalidSessionException) {
            status = HttpStatus.UNAUTHORIZED;
        } else if (ex instanceof LicensePlateAlreadyExistsException ||
                ex instanceof InvalidFieldsException ||
                ex instanceof MissingFieldsException ||
                ex instanceof InvalidLoginOrPasswordException ||
                ex instanceof EmailAlreadyExistsException ||
                ex instanceof LoginAlreadyExistsException) {
            status = HttpStatus.BAD_REQUEST;
        }

        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), status, request);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Internal Server Error", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}