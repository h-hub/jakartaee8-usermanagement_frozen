package com.harshajayamanna.usermanagement.controller;

import java.io.IOException;
import java.io.Serializable;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Named
@RequestScoped
public class LoginController implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String HOME_URL = "/includes/home.xhtml";
	
	@Email(message = "Must be a well-formed email address")
	@NotNull(message = "Cannot be empty")
	private String username;
	
	@NotNull(message = "Cannot be empty")
	private String password;
	
	@NotNull(message = "Please select an option")
	private String userType;

	private boolean remember;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public boolean isRemember() {
		return remember;
	}

	public void setRemember(boolean remember) {
		this.remember = remember;
	}

	public void submit() throws IOException {

		try {
			
			SecurityUtils.getSubject().login(new UsernamePasswordToken(username, password, remember));
            SavedRequest savedRequest = WebUtils.getAndClearSavedRequest(Faces.getRequest());
            Faces.redirect(savedRequest != null ? savedRequest.getRequestUrl() : HOME_URL);
            
		} catch  ( UnknownAccountException uae ) {
			Messages.addGlobalError("Unknown user, please try again");
		} catch  ( IncorrectCredentialsException ice ) {
			Messages.addGlobalError("Incorrect username or password");
		} catch  ( LockedAccountException lae ) {
			Messages.addGlobalError("Account Locked");
		} catch  ( ExcessiveAttemptsException eae ) {
			Messages.addGlobalError("Too many attemps");
		} catch ( AuthenticationException ae ) {
			Messages.addGlobalError("User not authorized");
		}

	}

	public void logOut() throws IOException {
		SecurityUtils.getSubject().logout();
		Faces.invalidateSession();
		Faces.redirect("/");
	}
}
