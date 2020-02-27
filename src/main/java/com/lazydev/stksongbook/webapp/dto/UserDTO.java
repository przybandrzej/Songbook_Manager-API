package com.lazydev.stksongbook.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String username;
    private UserRoleDTO userRole;
    private String firstName;
    private String lastName;

    // link user's songs
    // link user's playlists
    // link to self
    // link to all users
    // link userRoles
    // link to songs rated by user
}
