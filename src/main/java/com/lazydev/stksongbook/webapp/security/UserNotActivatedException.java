package com.lazydev.stksongbook.webapp.security;

public class UserNotActivatedException extends RuntimeException {

  public UserNotActivatedException(String msg) {
    super(msg);
  }
}
