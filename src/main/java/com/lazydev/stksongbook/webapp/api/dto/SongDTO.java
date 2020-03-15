package com.lazydev.stksongbook.webapp.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongDTO {

    private Long id;
    private Long categoryId;
    private String title;
    private List<SongAuthorDTO> authors;
    private String lyrics;
    private String guitarTabs;
    private String curio;
    private String additionTime;
    private List<Long> tagsId;

    // link to self
    // link to all songs

    /// link to all users' libraries that have this song in the library
    /// link to all public playlists that include this song
}
