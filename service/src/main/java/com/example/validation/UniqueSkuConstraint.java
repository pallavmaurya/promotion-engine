package com.example.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author pallavmaurya@gmail.com
 */
@Constraint(validatedBy = UniqueSkuConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueSkuConstraint {
    String message() default "SkuIds should be unique";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
