package com.lazydev.stksongbook.webapp.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.lazydev.stksongbook.webapp.service.validators.NameConstraint;
import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.Instant;

@Value
@JsonDeserialize(builder = UserDTO.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true)
public class UserDTO {

  @NotNull(message = "ID must be defined.")
  Long id;

  @NotNull(message = "Can't be null.")
  @Pattern(regexp = Constants.USERNAME_REGEX, message = Constants.USERNAME_INVALID_MESSAGE)
  String username;

  @NotNull(message = "Email can't be null.")
  @Pattern(regexp = Constants.EMAIL_REGEX, message = Constants.EMAIL_INVALID_MESSAGE)
  String email;

  @NotNull(message = "User Role ID must be defined.")
  Long userRoleId;

  @NameConstraint
  String firstName;

  @NameConstraint
  String lastName;

  boolean activated;

  Instant registrationDate;

  String imageUrl;

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {
  }
}
