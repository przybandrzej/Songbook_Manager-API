package com.lazydev.stksongbook.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongDTO {

    private Long id;
    private String title;
    private List<SongsAuthorsEntityDTO> authors;
    private CategoryDTO category;
    private String lyrics;
    private String guitarTabs;
    private String curio;
    private Double averageRating;
    private String additionTime;
    private List<TagDTO> tags;

    // link to self
    // link to all songs
    // link to all users' libraries that have this song in the library
    // link to all public playlists that include this song
}
