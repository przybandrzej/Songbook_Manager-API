package com.lazydev.stksongbook.webapp.util.validators;

import com.lazydev.stksongbook.webapp.util.Constants;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class NameValidator implements ConstraintValidator<NameConstraint, String> {
  @Override
  public void initialize(NameConstraint constraintAnnotation) {

  }

  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    return s == null || s.isEmpty() || Pattern.matches(Constants.NAME_REGEX, s);
  }
}
