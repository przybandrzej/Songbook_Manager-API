package com.lazydev.stksongbook.webapp.service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class EntityFieldValidationException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  private String object;
  private String field;
  private Object rejectedValue;
  private String message;
}
