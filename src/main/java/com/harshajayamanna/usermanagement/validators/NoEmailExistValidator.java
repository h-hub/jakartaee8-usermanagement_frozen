package com.harshajayamanna.usermanagement.validators;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.harshajayamanna.usermanagement.service.UserService;


public class NoEmailExistValidator implements ConstraintValidator<NoEmailExists, String> {

	@Inject
	private UserService userService;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {

		if (value==null || !userService.isEmailExists(value.toString())) {

			return true;

		}

		return false;
	}

}
