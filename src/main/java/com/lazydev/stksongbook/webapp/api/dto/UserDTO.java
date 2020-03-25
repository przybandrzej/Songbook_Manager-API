package com.lazydev.stksongbook.webapp.api.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class UserDTO {

    // link user's songs
    // link user's playlists
    // link to songs rated by user

    // link to self
    // link to all users
    // link userRoles

    private final Long id;
    private final String username;
    private final Long userRoleId;
    private final String firstName;
    private final String lastName;

    private UserDTO(Long id, String username, Long userRoleId, String firstName, String lastName) {
        this.id = id;
        this.username = username;
        this.userRoleId = userRoleId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static UserDTO.Builder builder() {
        return new UserDTO.Builder();
    }

    public static final class Builder {
        private Long id;
        private String username;
        private Long userRoleId;
        private String firstName;
        private String lastName;

        public UserDTO create() {
            return new UserDTO(id, username, userRoleId, firstName, lastName);
        }
        public UserDTO.Builder id(Long id) {
            this.id = id;
            return this;
        }
        public UserDTO.Builder username(String username) {
            this.username = username;
            return this;
        }
        public UserDTO.Builder userRoleId(Long id) {
            this.userRoleId = id;
            return this;
        }
        public UserDTO.Builder firstName(String name) {
            this.firstName = name;
            return this;
        }
        public UserDTO.Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
    }

    /*private List<Long> playlistIds;
    private List<ListSongDTO> songs;
    private List<UserSongRatingDTO> ratings;*/
}
