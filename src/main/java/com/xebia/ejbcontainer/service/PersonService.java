package com.xebia.ejbcontainer.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.xebia.ejbcontainer.domain.Person;

@Stateless
@Local
/**
 * Service permettant la gestion des entités Person avec la base de données
 * Seule la méthode de création est implémentée.
 */
public class PersonService implements IPersonService {

    @PersistenceContext
    EntityManager em;

    /**
     * Crée une nouvelle Person dans la base de données.
     * Méthode transactionnelle :
     *  @TransactionAttribute(TransactionAttributeType.REQUIRED) implicite
     * @param person une instance de Person
     * @return L'instance de personne persistée, champ Id initialisé
     */
    @Override
    public Person create(Person person) {
        em.persist(person);
        return person;
    }

}
