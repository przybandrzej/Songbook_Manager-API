package com.lazydev.stksongbook.webapp.service.dto.creational;

import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Value
@Builder
public class CreatePlaylistDTO {

  @Pattern(regexp = Constants.NAME_REGEX_SHORT, message = Constants.NAME_SHORT_INVALID_MESSAGE)
  String name;

  @NotNull(message = "Owner ID must be defined.")
  Long ownerId;

  @NotNull(message = "isPrivate field must be set.")
  Boolean isPrivate;

  @NotNull(message = "Songs list must be initialized.")
  Set<Long> songs;
}
