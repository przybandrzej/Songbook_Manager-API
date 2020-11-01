package com.lazydev.stksongbook.webapp.service.dto.change;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.lazydev.stksongbook.webapp.service.validators.NameConstraint;
import lombok.Builder;
import lombok.Value;

@Value
@JsonDeserialize(builder = NameChangeDTO.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true)
public class NameChangeDTO {

  @NameConstraint
  String name;

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {
  }
}
