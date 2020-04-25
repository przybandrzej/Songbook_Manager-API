package com.lazydev.stksongbook.webapp.service.exception;

public class UsernameAlreadyUsedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UsernameAlreadyUsedException(String username) {
        super("Username [" + username + "] already used!");
    }
}
