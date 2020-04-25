package com.lazydev.stksongbook.webapp.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Set;

@Getter
@EqualsAndHashCode
public class UserDTO {

  private final Long id;
  private final String username;
  private final Long userRoleId;
  private final String firstName;
  private final String lastName;
  private final Set<Long> songs;

  private UserDTO(Long id, String username, Long userRoleId, String firstName, String lastName, Set<Long> songs) {
    this.id = id;
    this.username = username;
    this.userRoleId = userRoleId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.songs = songs;
  }

  public static UserDTO.Builder builder() {
    return new UserDTO.Builder();
  }

  public static final class Builder {
    private Long id;
    private String username;
    private Long userRoleId;
    private String firstName;
    private String lastName;
    private Set<Long> songs;

    public UserDTO create() {
      return new UserDTO(id, username, userRoleId, firstName, lastName, songs);
    }

    public UserDTO.Builder id(Long id) {
      this.id = id;
      return this;
    }

    public UserDTO.Builder username(String username) {
      this.username = username;
      return this;
    }

    public UserDTO.Builder userRoleId(Long id) {
      this.userRoleId = id;
      return this;
    }

    public UserDTO.Builder firstName(String name) {
      this.firstName = name;
      return this;
    }

    public UserDTO.Builder lastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public UserDTO.Builder songs(Set<Long> songs) {
      this.songs = songs;
      return this;
    }
  }
}
