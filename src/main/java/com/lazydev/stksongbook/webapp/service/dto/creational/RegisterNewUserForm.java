package com.lazydev.stksongbook.webapp.service.dto.creational;

import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Pattern;

@Value
@Builder
public class RegisterNewUserForm {

  @Pattern(regexp = Constants.EMAIL_REGEX, message = Constants.EMAIL_INVALID_MESSAGE)
  String email;

  @Pattern(regexp = Constants.PASSWORD_REGEX, message = Constants.PASSWORD_REGEX)
  String password;

  @Pattern(regexp = Constants.USERNAME_REGEX, message = Constants.USERNAME_INVALID_MESSAGE)
  String username;

  @Pattern(regexp = Constants.NAME_REGEX, message = Constants.NAME_INVALID_MESSAGE)
  String firstName;

  @Pattern(regexp = Constants.NAME_REGEX, message = Constants.NAME_INVALID_MESSAGE)
  String lastName;
}
