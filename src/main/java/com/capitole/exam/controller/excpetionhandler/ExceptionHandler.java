package com.capitole.exam.controller.excpetionhandler;

import com.capitole.exam.exception.ApiException;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class ExceptionHandler extends ResponseEntityExceptionHandler {

  ResponseEntity<Object> getResponseEntity(ApiException e, WebRequest request, HttpStatus httpStatus) {
    Map<String, Object> body = Map.of(
        "error", e.getCode(),
        "message", e.getMessage());
    return handleExceptionInternal(e, body, new HttpHeaders(), httpStatus, request);
  }
}
