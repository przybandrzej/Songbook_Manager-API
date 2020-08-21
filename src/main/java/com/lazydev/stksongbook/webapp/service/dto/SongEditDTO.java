package com.lazydev.stksongbook.webapp.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Value
@JsonDeserialize(builder = SongEditDTO.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true)
public class SongEditDTO {

  @NotNull(message = "ID must be defined.")
  Long id;

  @NotNull(message = "Song ID must be defined.")
  Long editedSong;

  @NotNull(message = "User ID must be defined.")
  Long editedBy;

  Instant timestamp;

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {
  }
}
