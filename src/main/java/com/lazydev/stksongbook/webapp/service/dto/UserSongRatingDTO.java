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

  @NotNull(message = "ID must be defined.")
  Long id;

  @NotNull(message = "User ID must be defined.")
  Long userId;

  @NotNull(message = "Song ID must be defined.")
  Long songId;

  @NotNull(message = "Rating must be defined!")
  @DecimalMin(value = "0.0", message = "Rating must be minimum 0!")
  @DecimalMax(value = "1.0", message = "Rating must be maximum 1!")
  BigDecimal rating;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class Builder {
  }
}
