package com.oscaresteve.rentboot.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomErrorResponse {

  private String errorCode;
  private String message;
  private String detailedMessage;
  private String timestamp;

  public CustomErrorResponse(String errorCode, String message) {
    this.errorCode = errorCode;
    this.message = message;
    this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
  }

  public CustomErrorResponse(String errorCode, String message, String detailedMessage) {
    this.errorCode = errorCode;
    this.message = message;
    this.detailedMessage = detailedMessage;
    this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
  }

  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getDetailedMessage() {
    return detailedMessage;
  }

  public void setDetailedMessage(String detailedMessage) {
    this.detailedMessage = detailedMessage;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }
}
