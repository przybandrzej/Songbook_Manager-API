package com.lazydev.stksongbook.webapp.service.dto.creational;

import com.lazydev.stksongbook.webapp.service.validators.CoauthorFunctionConstraint;
import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCoauthorDTO {

  @NotBlank(message = "Field can't be blank.")
  @Pattern(regexp = Constants.NAME_REGEX_SHORT, message = Constants.NAME_SHORT_INVALID_MESSAGE)
  String authorName;

  @CoauthorFunctionConstraint
  String function;
}
