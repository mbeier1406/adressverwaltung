package com.github.mbeier1406.adressverwaltung.model;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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

	/** Das Objekt, mit dem getestet wird */
	public static Adresse a = new Adresse(12345, "Ort", "Straße 1", new PersonImpl("X", "X", new Date(), Geschlecht.WEIBLICH, null));

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

	/** Fügt einen Datensatz ein, der später abgefragt wird */
	@Ignore
	@Test
	public void testInsert() {
		dao.persist(a);
		LOGGER.info("id={}", a.getId());
	}

}
