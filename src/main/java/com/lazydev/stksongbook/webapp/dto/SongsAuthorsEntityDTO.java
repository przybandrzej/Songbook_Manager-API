package com.lazydev.stksongbook.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongsAuthorsEntityDTO {

    private Long authorId;
    private String name;
    private String function;

    // link to this author
    // link to self
    // link to all SongAuthors of this song
    // link link to the song
}
