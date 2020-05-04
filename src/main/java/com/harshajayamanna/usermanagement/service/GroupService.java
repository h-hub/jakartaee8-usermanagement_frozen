package com.harshajayamanna.usermanagement.service;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.harshajayamanna.usermanagement.entity.Group;


@Stateless
public class GroupService implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@PersistenceContext(name = "usermanagementDBPersistenceUnit")
    private EntityManager em;
	
	public Group getByName (String name) {
		
		Query groupQuery = em.createQuery("SELECT g FROM Group g WHERE g.name=:name");
		groupQuery.setParameter("name", name);
		
		return (Group) groupQuery.getSingleResult();
	}
	

}
