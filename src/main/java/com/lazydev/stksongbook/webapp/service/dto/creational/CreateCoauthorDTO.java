package com.lazydev.stksongbook.webapp.service.dto.creational;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.lazydev.stksongbook.webapp.service.validators.CoauthorFunctionConstraint;
import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Value
@JsonDeserialize(builder = CreateCoauthorDTO.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true)
public class CreateCoauthorDTO {

  @NotBlank(message = "Field can't be blank.")
  @Pattern(regexp = Constants.NAME_REGEX_SHORT, message = Constants.NAME_SHORT_INVALID_MESSAGE)
  String authorName;

  @CoauthorFunctionConstraint
  String coauthorFunction;

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {
  }
}
