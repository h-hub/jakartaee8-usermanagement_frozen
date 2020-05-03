package com.harshajayamanna.usermanagement.service;

import javax.mail.MessagingException;

import com.harshajayamanna.usermanagement.entity.User;


public interface UserService {
	
	public void addUser(String email, String password,String firstName, String lastName) throws MessagingException;
	
	public boolean isEmailExists(String email);
	
	public boolean activateUser(String key);
	
	public void sendPasswordResetLink(String email) throws MessagingException;
	
	public boolean validatePasswordResetKey(String key);
	
	public void resetPassword(String key, String newPassword);
	
	public User getUserByEmail(String email);

}
