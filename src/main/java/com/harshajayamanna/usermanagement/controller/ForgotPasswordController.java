/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.harshajayamanna.usermanagement.controller;


import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import com.harshajayamanna.usermanagement.service.UserService;
import com.harshajayamanna.usermanagement.validators.NoEmailExists;

/**
 *
 * @author harsha
 */
@Named
@RequestScoped
public class ForgotPasswordController  implements Serializable {
    
	private static final long serialVersionUID = 1L;

	@Inject
    private UserService userService;
    
	@NoEmailExists
	@NotNull(message = "Cannot be empty")
	@Email(message = "Must be a well-formed email address")	
    private String email;
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    public void sendLink() throws MessagingException {
        
        if (email.equals("")) {
			Messages.addGlobalError("Parameter cannot be empty.");
		} else {
			userService.sendPasswordResetLink(email);
			Faces.redirect("/includes/register/forgot-password-success.xhtml");
		}

    }
    
}
