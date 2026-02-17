package com.oscaresteve.rentboot.exception;

public class FiltroException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final String errorCode;

  public FiltroException(String message) {
    super(message);
    this.errorCode = "FILTER_ERROR";
  }

  public FiltroException(String errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public String getErrorCode() {
    return errorCode;
  }
}
