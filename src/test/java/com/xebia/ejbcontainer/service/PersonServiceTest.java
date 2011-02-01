package com.xebia.ejbcontainer.service;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.xebia.ejbcontainer.domain.Person;

public class PersonServiceTest {

    private static EJBContainer ejbContainer;

    private static IPersonService personService;

    /***
     * Méthode d'initialisation appelée une seule fois lors de l'exécution
     * des tests de PersonServiceTest.
     * C'est l'endroit idéal pour démarrer l'EJBContainer et récupérer
     * les EJB à tester.
     * @throws NamingException
     */
    @BeforeClass
    public static void init() throws NamingException {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(EJBContainer.MODULES, new File("target/classes.ext"));
        ejbContainer = EJBContainer.createEJBContainer(properties);
        Context ctx = ejbContainer.getContext();

        // le nom JNDI d'un EJB dépend du serveur d'application utilisé
        String personServiceName = PersonService.class.getSimpleName();
        personServiceName = isJbossContainer() ? personServiceName + "/local" : "java:global/classes.ext/" + personServiceName;
        personService = (IPersonService) ctx.lookup(personServiceName);
    }

    /***
     * Méthode de test qui vérifie la création d'un objet Person dans la
     * base de données. L'instance obtient un identifiant aprés avoir été
     * persistée par l'EntityManager.
     */
    @Test
    public void should_create_a_person() {
        Person person = new Person("Erich", "Gamma");
        Person createdPerson = personService.create(person);
        assertNotNull(createdPerson.getId());
    }

    /***
     * Méthode de nettoyage appelée une seule fois après l'exécution de
     * l'ensemble des tests unitaires de PersonServiceTest.
     * C'est l'endroit idéal pour fermer le contexte JNDI et l'EJBContainer.
     * @throws NamingException
     */
    @AfterClass
    public static void cleanup() throws NamingException {
        ejbContainer.close();
    }

    private static boolean isJbossContainer() {
        return System.getProperty("jboss.home") != null;
    }
}
