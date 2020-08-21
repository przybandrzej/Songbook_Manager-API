package com.lazydev.stksongbook.webapp.service.exception;

public class SuperUserAlreadyExistsException extends RuntimeException {

  public SuperUserAlreadyExistsException() {
    super("Superuser already exists!");
  }
}
