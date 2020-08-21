package com.lazydev.stksongbook.webapp.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.lazydev.stksongbook.webapp.service.validators.CoauthorFunctionConstraint;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@JsonDeserialize(builder = SongCoauthorDTO.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true)
public class SongCoauthorDTO {

  @NotNull(message = "Song ID must be defined.")
  Long songId;

  @NotNull(message = "Author ID must be defined.")
  Long authorId;

  @CoauthorFunctionConstraint
  String coauthorFunction;

  private SongCoauthorDTO(Long songId, Long authorId, String coauthorFunction) {
    this.songId = songId;
    this.authorId = authorId;
    this.coauthorFunction = coauthorFunction;
  }

  public static SongCoauthorDTO.Builder builder() {
    return new SongCoauthorDTO.Builder();
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static final class Builder {
  }
}
