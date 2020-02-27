package com.lazydev.stksongbook.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaylistDTO {

    private Long id;
    private String name;
    private UserDTO owner;
    private boolean isPrivate;
    private String creationTime;

    private List<SongDTO> songs;

    // link to self
    // link to songs within this playlist
    // link to all user's playlists
    // link to user
    // link to all playlists
}
