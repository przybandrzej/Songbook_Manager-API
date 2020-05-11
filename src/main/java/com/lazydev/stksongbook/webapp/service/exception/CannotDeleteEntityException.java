package com.lazydev.stksongbook.webapp.service.exception;

public class CannotDeleteEntityException extends RuntimeException {

  public CannotDeleteEntityException(String entity, String message) {
    super("Cannot delete " + entity + ". " + message);
  }
}
