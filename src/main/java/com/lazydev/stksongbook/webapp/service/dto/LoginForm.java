package com.lazydev.stksongbook.webapp.service.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LoginForm {
  String email;
  String password;
}
