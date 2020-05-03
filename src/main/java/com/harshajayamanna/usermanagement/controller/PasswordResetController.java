package com.harshajayamanna.usermanagement.controller;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.ManagedProperty;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import com.harshajayamanna.usermanagement.service.UserService;


@Named
@RequestScoped
public class PasswordResetController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "Cannot be empty")
	private String password;

	@Inject
	private UserService userService;

	@Inject
	@ManagedProperty("#{param.key}")
	@NotNull(message = "Cannot be empty")
	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void resetPassword() {
		
		userService.resetPassword(key, new Sha256Hash(password).toString());
		Faces.redirect("/includes/register/reset-password-success.xhtml");

	}

}
