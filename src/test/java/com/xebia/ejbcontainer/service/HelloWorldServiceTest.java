package com.xebia.ejbcontainer.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import junit.framework.Assert;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class HelloWorldServiceTest {

    private static EJBContainer ec;

    private static IHelloWorldService helloWorldService;

    /***
     * Méthode d'initialisation appelée une seule fois lors de l'exécution
     * des tests de HelloServiceTest.
     * C'est l'endroit idéal pour démarrer l'EJBContainer et récupérer
     * les EJB à tester.
     * @throws NamingException
     */
    @BeforeClass
    public static void init() throws NamingException {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(EJBContainer.MODULES, new File("target/classes.ext"));
        ec = EJBContainer.createEJBContainer(properties);
        Context ctx = ec.getContext();

        // le nom JNDI d'un EJB dépend du serveur d'application utilisé
        String helloWorldServiceName = HelloWorldService.class.getSimpleName();
        helloWorldServiceName = isJbossContainer() ? helloWorldServiceName + "/local" : "java:global/classes.ext/" + helloWorldServiceName;
        helloWorldService = (IHelloWorldService) ctx.lookup(helloWorldServiceName);

    }

    /***
     * Méthode de test qui vérifie que nous avons bien récupéré l'EJB
     * HelloWorldService et qu'il est fonctionnel
     */
    @Test
    public void should_say_hello_world() {
        Assert.assertEquals("Hello World", helloWorldService.sayHelloWorld());
    }

    /***
     * Méthode de nettoyage appelée une seule fois après l'exécution de
     * l'ensemble des tests unitaires de HelloServiceTest.
     * C'est l'endroit idéal pour fermer le contexte JNDI et l'EJBContainer.
     * Un bug de JBoss nous contraint à ne pas appeler les méthodes close()
     * sur context et container.
     * @throws NamingException
     */
    @AfterClass
    public static void cleanup() throws NamingException {
        if (!isJbossContainer()) {
            ec.close();
        }
    }

    private static boolean isJbossContainer() {
        return System.getProperty("jboss.home") != null;
    }

}
