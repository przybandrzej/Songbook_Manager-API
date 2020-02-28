package com.lazydev.stksongbook.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListSongDTO {

    private Long id;
    private String title;

    // link to self
    // link to the real song
    // link to all songs
}
