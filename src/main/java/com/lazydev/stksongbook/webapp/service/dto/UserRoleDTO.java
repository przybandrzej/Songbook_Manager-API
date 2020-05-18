package com.lazydev.stksongbook.webapp.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Value
@JsonDeserialize(builder = UserRoleDTO.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true)
public class UserRoleDTO {

  @NotNull(message = "ID must be defined.")
  Long id;

  @NotNull(message = "Can't be null.")
  @Pattern(regexp = Constants.NAME_REGEX_SHORT, message = Constants.NAME_SHORT_INVALID_MESSAGE)
  String name;

  private UserRoleDTO(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public static UserRoleDTO.Builder builder() {
    return new UserRoleDTO.Builder();
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static final class Builder {
    private Long id;
    private String name;

    public UserRoleDTO create() {
      return new UserRoleDTO(id, name);
    }

    public UserRoleDTO.Builder id(Long id) {
      this.id = id;
      return this;
    }

    public UserRoleDTO.Builder name(String name) {
      this.name = name;
      return this;
    }
  }
}
