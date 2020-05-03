/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.harshajayamanna.usermanagement.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.omnifaces.util.Faces;

/**
 *
 * @author harsha
 */
@Named
@RequestScoped
public class HomeController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private SecurityContext securityContext;

	private String userEmail;

	private String avtiveGroup;
	
	private String name;

	public String getUserEmail() {
		if (isLoggedIn()) {
			return securityContext.getCallerPrincipal().getName();
		}
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getAvtiveGroup() {
		return (String) Faces.getSession().getAttribute("activeGroup");
	}

	public void setAvtiveGroup(String avtiveGroup) {
		this.avtiveGroup = avtiveGroup;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private boolean isLoggedIn() {
		return securityContext != null && securityContext.getCallerPrincipal() != null;
	}
	
	@PostConstruct
	public void init() {
		Subject currentUser = SecurityUtils.getSubject();
		this.name = (String) currentUser.getSession().getAttribute("username");
	}

	public String logout() throws ServletException {
		getHttpRequestFromFacesContext().logout();
		return "/login.xhtml?faces-redirect=true";
	}

	private HttpServletRequest getHttpRequestFromFacesContext() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
}
