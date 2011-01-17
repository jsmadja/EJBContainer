package com.xebia.ejbcontainer.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;


import com.xebia.ejbcontainer.domain.Person;

@Stateless
@Local
public class PersonService implements IPersonService {

	@PersistenceContext
	EntityManager em;
	
	public Person create(Person person) {
		em.persist(person);
		return person;
	}
	
	public Person find(Long id) {
		return em.find(Person.class, id);
	}

	public List<Person> retrieveAll() {
		TypedQuery<Person> query = em.createNamedQuery("retrieveAllPersons", Person.class);
		return query.getResultList();
	}
	
	public Person update(Person person) {
		return em.merge(person);
	}
	
	public void delete(Person person) {
		em.remove(em.merge(person));
	}

	public void deleteAll() {
		em.createNamedQuery("deleteAllPersons").executeUpdate();
	}
	
}
