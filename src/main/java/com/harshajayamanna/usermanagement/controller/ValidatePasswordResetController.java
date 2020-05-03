package com.harshajayamanna.usermanagement.controller;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.ManagedProperty;
import javax.inject.Inject;
import javax.inject.Named;

import com.harshajayamanna.usermanagement.service.UserService;

@Named
@RequestScoped
public class ValidatePasswordResetController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
    private UserService userService;

    @Inject
    @ManagedProperty("#{param.key}")
    private String key;

    private boolean valid;
    
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @PostConstruct
    public void init() {

        if (key != "") {
            valid = userService.validatePasswordResetKey(key);
        }

    }

}
