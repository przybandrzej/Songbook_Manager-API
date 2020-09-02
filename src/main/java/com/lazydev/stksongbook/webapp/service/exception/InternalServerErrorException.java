package com.lazydev.stksongbook.webapp.service.exception;

public class InternalServerErrorException extends RuntimeException {

  public InternalServerErrorException(String msg) {
    super(msg);
  }
}
