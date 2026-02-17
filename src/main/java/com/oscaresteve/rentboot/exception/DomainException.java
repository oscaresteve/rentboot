package com.oscaresteve.rentboot.exception;

public class DomainException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final String errorCode;

  public DomainException(String message) {
    super(message);
    this.errorCode = "DOMAIN_ERROR";
  }

  public DomainException(String errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public String getErrorCode() {
    return errorCode;
  }
}
