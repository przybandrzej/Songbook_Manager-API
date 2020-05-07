package com.lazydev.stksongbook.webapp.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
public class UserSongRatingDTO {

    @NotNull(message = "User ID must be defined.")
    private final Long userId;

    @NotNull(message = "Song ID must be defined.")
    private final Long songId;

    @NotNull
    @DecimalMin("0.0") @DecimalMax("1.0")
    private final BigDecimal rating;

    private UserSongRatingDTO(Long userId, Long songId, BigDecimal userRating) {
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
        private BigDecimal userRating;

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
        public UserSongRatingDTO.Builder userRating(BigDecimal userRating) {
            this.userRating = userRating;
            return this;
        }
    }
}
