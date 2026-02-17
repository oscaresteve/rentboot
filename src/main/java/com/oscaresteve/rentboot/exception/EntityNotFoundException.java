package com.oscaresteve.rentboot.exception;

public class EntityNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final String errorCode;

  public EntityNotFoundException(String message) {
    super(message);
    this.errorCode = "ENTITY_NOT_FOUND";
  }

  public EntityNotFoundException(String errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public String getErrorCode() {
    return errorCode;
  }
}
