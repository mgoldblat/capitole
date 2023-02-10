package com.capitole.exam.controller.excpetionhandler;

import com.capitole.exam.exception.PriceNotFoundException;
import com.capitole.exam.exception.PriceSearchValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class PriceExceptionHandler extends ExceptionHandler {

  @org.springframework.web.bind.annotation.ExceptionHandler(value = {PriceNotFoundException.class})
  protected ResponseEntity<Object> handle(PriceNotFoundException e, WebRequest request) {
    return getResponseEntity(e, request, HttpStatus.NOT_FOUND);
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(value = {PriceSearchValidationException.class})
  protected ResponseEntity<Object> handle(PriceSearchValidationException e, WebRequest request) {
    return getResponseEntity(e, request, HttpStatus.UNPROCESSABLE_ENTITY);
  }
}
