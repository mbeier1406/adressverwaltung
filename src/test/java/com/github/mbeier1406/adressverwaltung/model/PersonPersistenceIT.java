package com.github.mbeier1406.adressverwaltung.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/**
 * Testet das Persistieren von {@linkplain Person}.<p/>
 * Integrationstest - kein Unit-Test: erfordert den Zugriff auf die Oracle-Datenbank.
 * @author mbeier
 */
public class PersonPersistenceIT {

	public static final Logger LOGGER = LogManager.getLogger(PersonPersistenceIT.class);

	@Test
	public void testePersitieren() {
		final var person = new Person("Max", "Mustermann");
		LOGGER.info("Person: {}", person);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("adressverwaltung");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(person);
		em.getTransaction().commit();
		em.close();
		emf.close();
	}

}
