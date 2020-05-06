package com.lazydev.stksongbook.webapp.service.dto;

import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Value
@Builder
public class UserDTO {

  Long id;
  String username;
  Long userRoleId;
  String firstName;
  String lastName;
  Set<Long> songs;
}
