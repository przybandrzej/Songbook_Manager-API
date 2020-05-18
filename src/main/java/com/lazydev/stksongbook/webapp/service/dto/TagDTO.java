package com.lazydev.stksongbook.webapp.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Value
@JsonDeserialize(builder = TagDTO.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true)
public class TagDTO {

  @NotNull(message = "ID must be defined.")
  Long id;

  @NotNull(message = "Can't be null.")
  @Pattern(regexp = Constants.NAME_REGEX_SHORT, message = Constants.NAME_SHORT_INVALID_MESSAGE)
  String name;

  private TagDTO(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public static TagDTO.Builder builder() {
    return new TagDTO.Builder();
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static final class Builder {
    private Long id;
    private String name;

    public TagDTO create() {
      return new TagDTO(id, name);
    }

    public TagDTO.Builder id(Long id) {
      this.id = id;
      return this;
    }

    public TagDTO.Builder name(String name) {
      this.name = name;
      return this;
    }
  }
}
