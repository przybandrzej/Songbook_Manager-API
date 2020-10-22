package com.lazydev.stksongbook.webapp.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
@JsonDeserialize(builder = LineDTO.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true)
public class LineDTO {

  @NotNull(message = "ID must be defined.")
  Long id;

  @NotNull(message = "Cannot be null.")
  @NotBlank(message = "Cannot be empty")
  String content;

  @NotNull(message = "Order must be defined.")
  Long order;

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {
  }
}
