package com.lazydev.stksongbook.webapp.api.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RegisterNewUserForm {
  String email;
  String password;
  String username;
  String firstName;
  String lastName;
}
