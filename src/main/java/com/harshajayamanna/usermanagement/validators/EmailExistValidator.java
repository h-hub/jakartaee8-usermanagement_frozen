package com.harshajayamanna.usermanagement.validators;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.harshajayamanna.usermanagement.service.UserService;


public class EmailExistValidator implements ConstraintValidator<EmailExists, String> {

	@Inject
	private UserService userService;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (value==null || !userService.isEmailExists(value.toString())) {

			return false;

		}

		return true;
	}

}
