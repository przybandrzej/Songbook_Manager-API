package com.lazydev.stksongbook.webapp.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Value
@JsonDeserialize(builder = UserSongRatingDTO.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true)
public class UserSongRatingDTO {

  @NotNull(message = "User ID must be defined.")
  Long userId;

  @NotNull(message = "Song ID must be defined.")
  Long songId;

  @NotNull(message = "Rating must be defined!")
  @DecimalMin(value = "0.0", message = "Rating must be minimum 0!")
  @DecimalMax(value = "1.0", message = "Rating must be maximum 1!")
  BigDecimal rating;

  private UserSongRatingDTO(Long userId, Long songId, BigDecimal userRating) {
    this.userId = userId;
    this.songId = songId;
    this.rating = userRating;
  }

  public static UserSongRatingDTO.Builder builder() {
    return new UserSongRatingDTO.Builder();
  }

  @JsonPOJOBuilder(withPrefix = "")
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
