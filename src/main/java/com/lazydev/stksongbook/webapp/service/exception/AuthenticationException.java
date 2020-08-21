package com.lazydev.stksongbook.webapp.service.exception;

public class AuthenticationException extends RuntimeException {

  public AuthenticationException() {
    super("There was a problem with authentication!");
  }
}
