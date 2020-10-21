package com.lazydev.stksongbook.webapp.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@JsonDeserialize(builder = VerseDTO.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true)
public class VerseDTO {

  @NotNull(message = "ID must be defined.")
  Long id;

  @NotNull(message = "Cannot be null.")
  boolean isChorus;

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {
  }
}
