package com.lazydev.stksongbook.webapp.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class UserSongRatingDTO {

    private final Long userId;
    private final Long songId;
    private final Double rating;

    private UserSongRatingDTO(Long userId, Long songId, Double userRating) {
        this.userId = userId;
        this.songId = songId;
        this.rating = userRating;
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
