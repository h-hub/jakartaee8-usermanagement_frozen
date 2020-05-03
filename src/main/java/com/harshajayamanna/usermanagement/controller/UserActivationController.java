package com.harshajayamanna.usermanagement.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.harshajayamanna.usermanagement.service.UserService;


@Named
@RequestScoped
public class UserActivationController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
    private UserService userService;

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
        key = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("key");
        valid = userService.activateUser(key);
    }

}
