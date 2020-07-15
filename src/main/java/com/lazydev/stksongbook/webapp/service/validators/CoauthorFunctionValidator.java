package com.lazydev.stksongbook.webapp.service.validators;

import com.lazydev.stksongbook.webapp.util.Constants;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CoauthorFunctionValidator implements ConstraintValidator<CoauthorFunctionConstraint, String> {

  @Override
  public void initialize(CoauthorFunctionConstraint constraintAnnotation) { }

  @Override
  public boolean isValid(String provided, ConstraintValidatorContext constraintValidatorContext) {
    return provided != null && (provided.equals(Constants._Function_Music_Polish.replace("'", "")) || provided.equals(Constants._Function_Text_Polish.replace("'", "")));
  }
}
