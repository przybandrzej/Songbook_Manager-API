package com.lazydev.stksongbook.webapp.service.exception;

public class NotAuthenticatedException extends RuntimeException {

  public NotAuthenticatedException() {
    super("Not Authorized!");
  }
}
