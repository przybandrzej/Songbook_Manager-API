package com.lazydev.stksongbook.webapp.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;

public @Data
class Song {
    private int id;
    private Author author;
    private String title;
    private String lyrics;
    private String guitarTabs;
    private ArrayList<Tag> tags;
    private String curio;
    private LocalDateTime additionTime;
    private Category category;
}
