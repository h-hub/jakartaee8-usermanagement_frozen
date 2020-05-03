package com.harshajayamanna.usermanagement.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordCheck, Object> {
	
	private String password;
    private String secondFieldName;

	@Override
	public void initialize(PasswordCheck constraintAnnotation) {
		password = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
	}

	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext context) {
		System.out.println(password);
		return false;
	}
}