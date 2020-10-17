package com.lazydev.stksongbook.webapp.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Value
@JsonDeserialize(builder = EmailChangeDTO.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true)
public class EmailChangeDTO {

  @NotNull(message = "Email can't be null.")
  @Pattern(regexp = Constants.EMAIL_REGEX, message = Constants.EMAIL_INVALID_MESSAGE)
  String email;

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {
  }
}
