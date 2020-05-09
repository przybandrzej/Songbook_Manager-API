package com.lazydev.stksongbook.webapp.service.dto.creational;

import com.lazydev.stksongbook.webapp.util.Constants;
import com.lazydev.stksongbook.webapp.service.validators.CoauthorFunctionConstraint;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Pattern;

@Value
@Builder
public class CreateCoauthorDTO {

  @Pattern(regexp = Constants.NAME_REGEX_SHORT, message = Constants.NAME_SHORT_INVALID_MESSAGE)
  String authorName;

  @CoauthorFunctionConstraint
  String function;
}
