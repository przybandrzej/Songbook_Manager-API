package com.lazydev.stksongbook.webapp.model;

import lombok.Data;

public @Data
class Tag {

    private int id;
    private String tagName;
    private static char tagPrefix = '#';
}
