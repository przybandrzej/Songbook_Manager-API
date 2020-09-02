package com.lazydev.stksongbook.webapp.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.lazydev.stksongbook.webapp.data.model.enumeration.CoauthorFunction;
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

  @NotNull(message = "Function cannot be null.")
  CoauthorFunction coauthorFunction;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class Builder {
  }
}
