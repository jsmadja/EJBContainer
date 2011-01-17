package com.xebia.ejbcontainer.service;

import java.util.List;

import com.xebia.ejbcontainer.domain.Person;

public interface IPersonService {

	Person create(Person person);
	Person find(Long id);
	List<Person> retrieveAll();
	Person update(Person person);
	void delete(Person person);
	void deleteAll();
}
