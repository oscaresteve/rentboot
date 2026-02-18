package com.oscaresteve.rentboot.exception;

import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<CustomErrorResponse> handleNotFound(EntityNotFoundException ex) {
    CustomErrorResponse error = new CustomErrorResponse(ex.getErrorCode(), ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(FiltroException.class)
  public ResponseEntity<CustomErrorResponse> handleFilter(FiltroException ex) {
    CustomErrorResponse error = new CustomErrorResponse(ex.getErrorCode(), ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler({
      MethodArgumentTypeMismatchException.class,
      HttpMessageNotReadableException.class,
      IllegalArgumentException.class
  })
  public ResponseEntity<CustomErrorResponse> handleBadRequest(Exception ex) {
    CustomErrorResponse error = new CustomErrorResponse("BAD_REQUEST", "Solicitud invalida", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<CustomErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
    String details = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(this::formatFieldError)
        .collect(Collectors.joining("; "));

    CustomErrorResponse error = new CustomErrorResponse("VALIDATION_ERROR", "Error de validacion de campos", details);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<CustomErrorResponse> handleConstraintValidation(ConstraintViolationException ex) {
    CustomErrorResponse error = new CustomErrorResponse("VALIDATION_ERROR", "Error de validacion", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<CustomErrorResponse> handleIntegrity(DataIntegrityViolationException ex) {
    CustomErrorResponse error = new CustomErrorResponse(
        "INTEGRITY_VIOLATION",
        "Violacion de restricciones de integridad",
        ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  @ExceptionHandler(DomainException.class)
  public ResponseEntity<CustomErrorResponse> handleDomain(DomainException ex) {
    CustomErrorResponse error = new CustomErrorResponse(ex.getErrorCode(), ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<CustomErrorResponse> handleAuthentication(AuthenticationException ex) {
    CustomErrorResponse error = new CustomErrorResponse("UNAUTHORIZED", "Credenciales invalidas", ex.getMessage());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<CustomErrorResponse> handleGeneric(Exception ex) {
    CustomErrorResponse error = new CustomErrorResponse("INTERNAL_SERVER_ERROR", "Error interno del servidor", ex.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }

  private String formatFieldError(FieldError fieldError) {
    return fieldError.getField() + ": " + fieldError.getDefaultMessage();
  }
}
