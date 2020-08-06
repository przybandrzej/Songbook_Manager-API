package com.lazydev.stksongbook.webapp.service.exception;

public class EntityDependentNotInitialized extends RuntimeException {

  public EntityDependentNotInitialized(String entityName) {
    super("Entity [" + entityName + "] was not found in the system.");
  }
}
