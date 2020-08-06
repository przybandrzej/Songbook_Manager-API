package com.lazydev.stksongbook.webapp.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Value
@JsonDeserialize(builder = SongAddDTO.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true)
public class SongAddDTO {

  @NotNull(message = "ID must be defined.")
  Long id;

  @NotNull(message = "Song ID must be defined.")
  Long addedSong;

  @NotNull(message = "User ID must be defined.")
  Long addedBy;

  Instant timestamp;

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {
  }
}
