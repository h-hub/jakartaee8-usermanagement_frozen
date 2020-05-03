package com.harshajayamanna.usermanagement.entity;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "user_activation")
public class UserActivation {

	@Id
	private String id;

	@OneToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID")
	private User user;

	public UserActivation() {
		super();
	}

	public UserActivation(User user) {
		super();
		this.id = UUID.randomUUID().toString();
		this.user = user;
	}

	public String getId() {
		return id;
	}

	public void setId(String key) {
		this.id = key;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof UserActivation)) {
			return false;
		}
		UserActivation other = (UserActivation) obj;
		return getId().equals(other.getId());
	}

}
