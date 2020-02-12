package com.lazydev.stksongbook.webapp.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;

public @Data
class Playlist {
    private int id;
    private int ownerId;
    private String name;
    private LocalDateTime creationTime;
    private ArrayList<Song> songs;
}
