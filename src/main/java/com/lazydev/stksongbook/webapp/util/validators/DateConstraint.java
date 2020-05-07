package com.lazydev.stksongbook.webapp.util.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {})
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DateConstraint {
  String message() default "Invalid date.";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
