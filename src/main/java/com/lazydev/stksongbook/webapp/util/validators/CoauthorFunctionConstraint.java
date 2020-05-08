package com.lazydev.stksongbook.webapp.util.validators;

import com.lazydev.stksongbook.webapp.util.Constants;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {CoauthorFunctionValidator.class})
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CoauthorFunctionConstraint {
  String message() default "Invalid function. Must be one of the following: "
      + Constants._Function_Music_Polish + " or " + Constants._Function_Text_Polish + ".";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
