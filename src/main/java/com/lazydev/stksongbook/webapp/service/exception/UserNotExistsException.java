package com.lazydev.stksongbook.webapp.service.exception;

public class UserNotExistsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserNotExistsException(String identityValue) {
        super("The user [" + identityValue + "] does not exist!");
    }

    public UserNotExistsException(Long id) {
        super("The user with ID [" + id + "] does not exist!");
    }
}
