package com.harshajayamanna.usermanagement.validators;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = { NoEmailExistValidator.class})
public @interface NoEmailExists {
	
	String message() default "Email doesn not Exists. Please sign up.";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

}
