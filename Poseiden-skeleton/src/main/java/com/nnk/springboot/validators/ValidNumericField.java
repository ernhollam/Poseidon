package com.nnk.springboot.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Custom annotation for decimal fields validation.<p></p>
 * Source:
 * <a href=https://docs.jboss.org/hibernate/validator/8.0/reference/en-US/html_single/#validator-customconstraints-constraintannotation>Creating custom constraints</a>
 */
@Target({FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = NumericFieldValidator.class)
@Documented
public @interface ValidNumericField {
    String message() default "Please enter a valid number";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
