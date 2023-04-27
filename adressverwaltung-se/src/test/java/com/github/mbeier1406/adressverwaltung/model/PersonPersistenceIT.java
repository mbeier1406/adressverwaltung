package com.github.mbeier1406.adressverwaltung.model;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/**
 * Testet das Persistieren von {@linkplain PersonImpl} mit einer {@linkplain Adresse} und den
 * Abruf der Adresse mit dem Laden der zugehörigen Person.
 * <p/>
 * Integrationstest - kein Unit-Test: erfordert den Zugriff auf die Oracle-Datenbank.
 * @author mbeier
 */
public class PersonPersistenceIT {

	public static final Logger LOGGER = LogManager.getLogger(PersonPersistenceIT.class);

	@Test
	public void testePersitieren() {
		final var person = new PersonImpl("Friedl", "Max", new Date(), Person.Geschlecht.MAENNLICH, null); // Person erzeugen, Adresse fehlt noch
		final var adresse = new Adresse(12345, "Ort", "Strasse 1", (PersonImpl) person); // Adresse erzeugen und Person setzen
		person.setAdresse(adresse); // Adresse nun wieder der Person zuordnen
		LOGGER.info("Person: {}", person.toInfo());
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("adressverwaltung");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(person);
		LOGGER.info("person={}", person.toInfo());
		em.getTransaction().commit();
		final var adresse2 = em.find(Adresse.class, person.getAdresse().getId()); // Adresse über die zuvor vergebene ID laden.
		LOGGER.info("adresse2={}; person2={}", adresse2, adresse2.getPerson().toInfo()); // Person wurde mit geladen
		em.close();
		emf.close();
	}

}
