package com.lazydev.stksongbook.webapp.service.dto;

import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Value
@Builder
public class UserDTO {

  @NotNull(message = "ID must be defined.")
  Long id;

  @Pattern(regexp = Constants.USERNAME_REGEX, message = Constants.USERNAME_INVALID_MESSAGE)
  String username;

  @NotNull(message = "User Role ID must be defined.")
  Long userRoleId;

  @Pattern(regexp = Constants.NAME_REGEX, message = Constants.NAME_INVALID_MESSAGE)
  String firstName;

  @Pattern(regexp = Constants.NAME_REGEX, message = Constants.NAME_INVALID_MESSAGE)
  String lastName;

  @NotNull(message = "Songs list must be initialized.")
  Set<Long> songs;
}
