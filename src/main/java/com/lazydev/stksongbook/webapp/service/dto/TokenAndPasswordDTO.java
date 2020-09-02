package com.lazydev.stksongbook.webapp.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@JsonDeserialize(builder = TokenAndPasswordDTO.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true)
public class TokenAndPasswordDTO {

  String token;
  String newPassword;

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {
  }
}
