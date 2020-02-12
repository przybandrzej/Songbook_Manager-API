package com.lazydev.stksongbook.webapp.model;

import lombok.Data;

public @Data
class User {
    private int id;
    private String userName;
    private String password;
    private String displayName;
    private int addedSongsCount;
    private int editedSongsCount;
    private UserRole userRole;
}
