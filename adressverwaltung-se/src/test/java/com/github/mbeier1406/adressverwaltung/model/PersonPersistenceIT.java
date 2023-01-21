package com.github.mbeier1406.adressverwaltung.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/**
 * Testet das Persistieren von {@linkplain PersonImpl}.<p/>
 * Integrationstest - kein Unit-Test: erfordert den Zugriff auf die Oracle-Datenbank.
 * @author mbeier
 */
public class PersonPersistenceIT {

	public static final Logger LOGGER = LogManager.getLogger(PersonPersistenceIT.class);

	@Test
	public void testePersitieren() {
		Person person = new PersonImpl("Max", "Mustermann", new Date(), Person.Geschlecht.MAENNLICH, null);
		LOGGER.info("Person: {}", person);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("adressverwaltung");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
//		em.persist(person);
		person = Objects.requireNonNull(em.find(PersonImpl.class, 1L), "Nicht vorhanden!");
		LOGGER.info("person={}", person);
//		person.setVorname("Eva");
		em.remove(person);
		em.getTransaction().commit();
		em.close();
		emf.close();
	}

}
