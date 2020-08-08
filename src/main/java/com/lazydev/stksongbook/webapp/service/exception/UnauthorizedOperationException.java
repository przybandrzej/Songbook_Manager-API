package com.lazydev.stksongbook.webapp.service.exception;

public class UnauthorizedOperationException extends RuntimeException {

  public UnauthorizedOperationException(String msg) {
    super("Unauthorized operation! " + msg);
  }
}
