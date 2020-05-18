package com.lazydev.stksongbook.webapp.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@JsonDeserialize(builder = LoginForm.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true)
public class LoginForm {

  String email;
  String password;

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {
  }
}
