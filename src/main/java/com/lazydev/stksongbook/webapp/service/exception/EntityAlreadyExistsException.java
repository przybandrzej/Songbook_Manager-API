package com.lazydev.stksongbook.webapp.service.exception;

public class EntityAlreadyExistsException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public EntityAlreadyExistsException(String entity, Long entityId, String name) {
    super(entity + " [ID " + entityId + " " + name + "] already exists!");
  }

  public EntityAlreadyExistsException(String entity, String name) {
    super(entity + " [" + name + "] already exists!");
  }

  public EntityAlreadyExistsException(String entity) {
    super(entity + " already exists!");
  }
}
