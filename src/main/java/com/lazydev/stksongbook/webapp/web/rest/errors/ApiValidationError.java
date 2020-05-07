package com.lazydev.stksongbook.webapp.web.rest.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
class ApiValidationError extends SubError {

  private String parameter;
  private String type;
  private String message;
}
