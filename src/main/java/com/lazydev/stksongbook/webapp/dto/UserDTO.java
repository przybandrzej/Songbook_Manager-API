package com.lazydev.stksongbook.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String username;
    private Long userRoleId;
    private String firstName;
    private String lastName;
    private List<Long> playlistIds;
    private List<ListSongDTO> songs;
    private List<UsersSongsRatingsEntityDTO> ratings;

    // link user's songs
    // link user's playlists
    // link to songs rated by user

    // link to self
    // link to all users
    // link userRoles
}
