package com.capitole.exam.exception;

public class PriceNotFoundException extends ApiException {
  public PriceNotFoundException(String code, String message) {
    super(code, message);
  }
}
