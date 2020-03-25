package com.lazydev.stksongbook.webapp.api.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class UserSongRatingDTO {

    // link to self
    // link to the real song
    // link to all user's songs ratings
    // link to user

    private final Long userId;
    private final Long songId;
    private final Double userRating;

    private UserSongRatingDTO(Long userId, Long songId, Double userRating) {
        this.userId = userId;
        this.songId = songId;
        this.userRating = userRating;
    }

    public static UserSongRatingDTO.Builder builder() {
        return new UserSongRatingDTO.Builder();
    }

    public static final class Builder {
        private Long userId;
        private Long songId;
        private Double userRating;

        public UserSongRatingDTO create() {
            return new UserSongRatingDTO(userId, songId, userRating);
        }

        public UserSongRatingDTO.Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }
        public UserSongRatingDTO.Builder songId(Long songId) {
            this.songId = songId;
            return this;
        }
        public UserSongRatingDTO.Builder userRating(Double userRating) {
            this.userRating = userRating;
            return this;
        }
    }
}
