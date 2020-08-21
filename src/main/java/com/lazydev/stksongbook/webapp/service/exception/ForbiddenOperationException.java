package com.lazydev.stksongbook.webapp.service.exception;

public class ForbiddenOperationException extends RuntimeException {

  public ForbiddenOperationException(String msg) {
    super("Forbidden operation! " + msg);
  }
}
