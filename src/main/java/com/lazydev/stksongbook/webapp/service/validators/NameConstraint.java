package com.lazydev.stksongbook.webapp.service.validators;

import com.lazydev.stksongbook.webapp.util.Constants;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {NameValidator.class})
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NameConstraint {
  String message() default Constants.NAME_INVALID_MESSAGE;
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
