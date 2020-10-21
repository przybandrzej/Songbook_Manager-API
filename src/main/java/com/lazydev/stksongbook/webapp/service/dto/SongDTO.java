package com.lazydev.stksongbook.webapp.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.Builder;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Value
@JsonDeserialize(builder = SongDTO.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true)
public class SongDTO {

  @NotNull(message = "ID must be defined.")
  Long id;

  @Valid
  AuthorDTO author;

  @Valid
  CategoryDTO category;

  @NotNull(message = "Can't be null.")
  @Pattern(regexp = Constants.NAME_REGEX_SHORT, message = Constants.TITLE_INVALID_MESSAGE)
  String title;

  String trivia;

  BigDecimal averageRating;

  Boolean isAwaiting;

  Long addedBy;

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {
  }
}
