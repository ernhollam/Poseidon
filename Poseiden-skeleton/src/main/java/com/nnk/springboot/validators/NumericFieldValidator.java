package com.nnk.springboot.validators;

import com.nnk.springboot.constants.NumericValueRegExp;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validator for numeric fields.<br>
 * Sources:
 * <li>
 * <a
 * href="https://docs.jboss.org/hibernate/validator/8.0/reference/en-US/html_single/#section-constraint-validator">The
 * contraint validator</a>
 * </li>
 * <li>
 * <a
 * href="https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/math/NumberUtils
 * .html#isParsable-java.lang.String-">isParsable()</a></li>
 */
public class NumericFieldValidator implements ConstraintValidator<ValidNumericField, Object> {

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {

        if (object == null) {
            // disable existing violation message
            constraintValidatorContext.disableDefaultConstraintViolation();
            customMessageForValidation(constraintValidatorContext);
            return false;
        }
        return String.valueOf(object).matches(object instanceof Double ? NumericValueRegExp.DOUBLE :
                                              NumericValueRegExp.INTEGER);
    }

    private void customMessageForValidation(ConstraintValidatorContext constraintContext) {
        // build new violation message and add it
        constraintContext.buildConstraintViolationWithTemplate("Value is mandatory").addConstraintViolation();
    }
}
