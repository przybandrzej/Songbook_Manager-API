package com.lazydev.stksongbook.webapp.service.exception;

public class BadRequestErrorException extends RuntimeException {

  public BadRequestErrorException(String msg) {
    super(msg);
  }
}
