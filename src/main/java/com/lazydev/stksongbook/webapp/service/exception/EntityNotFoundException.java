package com.lazydev.stksongbook.webapp.service.exception;

public class EntityNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public EntityNotFoundException(Class clazz) {
    super(clazz.getSimpleName() + " not found!");
  }

  public EntityNotFoundException(Class clazz, Long id) {
    super(clazz.getSimpleName() + " with ID [" + id + "] not found!");
  }

  public EntityNotFoundException(Class clazz, String id) {
    super(clazz.getSimpleName() + " with unique name [" + id + "] not found!");
  }

  public EntityNotFoundException(Class clazz, Long id, Class clazz2, Long id2) {
    super(clazz.getSimpleName() + " with ID [" + id + "] not found in relation to "
        + clazz2.getSimpleName() + " with ID [" + id2 + "]!");
  }
}