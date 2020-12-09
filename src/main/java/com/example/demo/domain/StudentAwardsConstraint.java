package com.example.demo.domain;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StudentAwardsConstraintValidator.class)

public @interface StudentAwardsConstraint {
    String message() default "write surname properly \n";
    Class<?> [] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
