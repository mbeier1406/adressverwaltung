package com.github.mbeier1406.adressverwaltung.model;

import java.util.Date;

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
		Person person = new PersonImpl("Friedl", "Max", new Date(), Person.Geschlecht.MAENNLICH, null);
		final var adresse = new Adresse(12345, "Ort", "Strasse 1", (PersonImpl) person);
		person.setAdresse(adresse);
		LOGGER.info("Person: {}", person);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("adressverwaltung");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(person);
//		person = Objects.requireNonNull(em.find(PersonImpl.class, 1000L), "Nicht vorhanden!");
		LOGGER.info("person={}", person);
//		em.remove(person);
		em.getTransaction().commit();
		final var adresse2 = em.find(Adresse.class, person.getAdresse().getId());
		LOGGER.info("adresse2={}", adresse2);
		LOGGER.info("person2={}", adresse2.getPerson());
		em.close();
		emf.close();
	}

}
