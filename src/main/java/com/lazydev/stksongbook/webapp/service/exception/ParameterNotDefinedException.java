package com.lazydev.stksongbook.webapp.service.exception;

public class ParameterNotDefinedException extends RuntimeException {

  public ParameterNotDefinedException(String param) {
    super("Parameter '" + param + "' must be defined!");
  }
}
