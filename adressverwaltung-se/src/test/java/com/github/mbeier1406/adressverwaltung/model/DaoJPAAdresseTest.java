package com.github.mbeier1406.adressverwaltung.model;

import java.util.Date;

import javax.persistence.RollbackException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.mbeier1406.adressverwaltung.Dao;
import com.github.mbeier1406.adressverwaltung.DaoJPA;
import com.github.mbeier1406.adressverwaltung.model.Person.Geschlecht;

/**
 * Tests für die Klasse {@linkplain DaoJPA}.
 * @author mbeier
 */
public class DaoJPAAdresseTest {

	public static final Logger LOGGER = LogManager.getLogger(DaoJPAAdresseTest.class);

	/** Das zu testende Objekt */
	public static Dao<Adresse> dao;

	/** DAO initialisieren */
	@Before
	public void init() {
		LOGGER.info("DB verbinden...");
		dao = new DaoJPA<>(Adresse.class);
	}

	/** Zuletzt Datenbank schließen */
	@After
	public void cleanup() {
		LOGGER.info("DB schließen...");
		dao.shutdown();
	}

	/** Kann nicht gespeichert werden, da {@linkplain Adresse#getPerson()} transient: <code>object references an unsaved transient instance</code> */
	@Test(expected = RollbackException.class)
	public void insertMitTransienterPersonSchlaegtFehl() {
		// Das Objekt, mit dem getestet wird: Person-Objekt ist im Status "transient"
		final var a = new Adresse(12345, "Ort", "Straße 1", new PersonImpl("X", "X", new Date(), Geschlecht.WEIBLICH, null));
		dao.persist(a);
	}

	/** Kann gespeichert werden, da {@linkplain Adresse#getPerson()} nicht gesetzt ist */
	@Test
	public void insertOhnePersonIstMoeglich() {
		// Das Objekt, mit dem getestet wird: Person-Objekt ist NULL
		final var a = new Adresse(12345, "Ort", "Straße 1", null);
		dao.persist(a);
	}

}
