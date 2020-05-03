package com.harshajayamanna.usermanagement.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.harshajayamanna.usermanagement.service.UserService;
import com.harshajayamanna.usermanagement.validators.EmailExists;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

@Named
@RequestScoped
//@PasswordCheck(first = "password", second = "retypedPassword")
public class RegisterController implements Serializable {

	@Inject
	private UserService userService;

	private static final long serialVersionUID = 1L;

	@NotNull(message = "Cannot be empty")
	@Email(message = "Must be a well-formed email address")
	@EmailExists
	private String email;

	@NotNull(message = "Cannot be empty")
	@Size(min=6)
	private String password;

	@NotNull(message = "Cannot be empty")
	private String firstName;

	@NotNull(message = "Cannot be empty")
	private String lastName;

	public RegisterController() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public void signUp() {

		try {
			userService.addUser(email, password, firstName, lastName);
			Faces.redirect("/includes/user/register-success.xhtml");
		} catch (MessagingException e) {
			Messages.addGlobalError("Failed to send the email.");
		}
	}

	@Override
	public String toString() {
		return "RegisterController [email=" + email + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + "]";
	}

}
