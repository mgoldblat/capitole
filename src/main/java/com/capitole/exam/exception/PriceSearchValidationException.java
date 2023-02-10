package com.capitole.exam.exception;

public class PriceSearchValidationException extends ApiException {
  public PriceSearchValidationException(String code, String message) {
    super(code, message);
  }

}
