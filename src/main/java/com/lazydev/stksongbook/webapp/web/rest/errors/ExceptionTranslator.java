package com.lazydev.stksongbook.webapp.web.rest.errors;

import com.lazydev.stksongbook.webapp.service.exception.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionTranslator extends ResponseEntityExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
    Error apiError = new Error(NOT_FOUND);
    apiError.setMessage(ex.getMessage());
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(UserNotExistsException.class)
  protected ResponseEntity<Object> handleUserNotExists(UserNotExistsException ex, WebRequest request) {
    Error apiError = new Error(NOT_FOUND);
    apiError.setMessage(ex.getMessage());
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(InvalidPasswordException.class)
  protected ResponseEntity<Object> handleInvalidPassword(InvalidPasswordException ex, WebRequest request) {
    Error apiError = new Error(UNAUTHORIZED);
    apiError.setMessage(ex.getMessage());
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(IOException.class)
  protected ResponseEntity<Object> handleIOException(IOException ex, WebRequest request) {
    Error error = new Error(INTERNAL_SERVER_ERROR);
    error.setMessage("Critical error occurred while working with the file! Internal message: " + ex.getMessage());
    return buildResponseEntity(error);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                HttpStatus status, WebRequest request) {
    List<SubError> errors = new ArrayList<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.add(new ApiValidationError(error.getField(),
          error.getRejectedValue() == null ? "null" : error.getRejectedValue().toString(), error.getDefaultMessage()));
    }
    for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
      errors.add(new ApiValidationError(error.getObjectName(), null,  error.getDefaultMessage()));
    }
    Error apiError = new Error(HttpStatus.BAD_REQUEST, "Validation failed", errors);
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
    List<SubError> errors = new ArrayList<>();
    for (ConstraintViolation<?> error : ex.getConstraintViolations()) {
      String field = null;
      for (Path.Node node : error.getPropertyPath()) {
        field = node.getName();
      }
      errors.add(new ApiValidationError(field,
          error.getInvalidValue() == null ? null : error.getInvalidValue().toString(), error.getMessage()));
    }
    Error apiError = new Error(HttpStatus.BAD_REQUEST, "Validation failed", errors);
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler({ EmailAlreadyUsedException.class,
      UsernameAlreadyUsedException.class,
      EntityAlreadyExistsException.class,
      FileNotFoundException.class,
      StorageException.class })
  protected ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) {
    Error apiError = new Error(BAD_REQUEST);
    apiError.setMessage(ex.getMessage());
    return buildResponseEntity(apiError);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                HttpHeaders headers, HttpStatus status,
                                                                WebRequest request) {
    String error = "Malformed JSON request";
    return buildResponseEntity(new Error(HttpStatus.BAD_REQUEST, error));
  }

  private ResponseEntity<Object> buildResponseEntity(Error apiError) {
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }
}
