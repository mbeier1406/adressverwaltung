package com.github.mbeier1406.adressverwaltung.model;

import static com.github.mbeier1406.adressverwaltung.model.Person.Geschlecht.MAENNLICH;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Tests für die Klasse {@linkplain DaoJPA}.
 * @author mbeier
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DaoJPATest {

	public static final Logger LOGGER = LogManager.getLogger(DaoJPATest.class);

	/** Das zu testende Objekt */
	public static Dao<PersonImpl> dao;

	/** Das Objekt, mit dem getestet wird */
	public static PersonImpl p;
	static {
		try {
			p = new PersonImpl("Kulle", "Wolters",
					new Date(), MAENNLICH,
					Files.readAllBytes(Paths.get("src/main/resources/images/maxmustermann.png")));
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static long id = 0;

	/** DAO initialisieren */
	@BeforeClass
	public static void init() {
		LOGGER.info("DB verbinden...");
		dao = new DaoJPA<>(PersonImpl.class);
	}

	/** Zuletzt Datenbank schließen */
	@AfterClass
	public static void cleanup() {
		LOGGER.info("DB schließen...");
		dao.shutdown();
	}

	/** Stellt sicher, dass die Klasse im Generic korrekt übernommen wird */
	@Test
	public void a_testeToString() {
		LOGGER.info("dao={}", dao);
		assertThat(dao.toString(), equalTo("DaoJPA [c=class com.github.mbeier1406.adressverwaltung.model.PersonImpl]"));
	}

	/** Fügt einen Datensatz ein, der später abgefragt wird */
	@Test
	public void b_testInsert() {
		dao.persist(p);
		LOGGER.info("id={}", id=p.getId());
	}

	/** Datensatz anhand der in {@linkplain #b_testInsert()} vergebenen {@linkplain #id} wieder einlesen */
	@Test
	public void c_testeFindById() {
		final var person = dao.findById(id);
		LOGGER.info("person={}", person);
		assertThat(person, equalTo(p));
	}

	/** Datensatz anhand eines der in {@linkplain #b_testInsert()} vergebenen Proprties wieder einlesen */
	@Test
	public void d_testeFindByProperty() {
		final var person = dao.findByProperty("vorname", "Kulle");
		LOGGER.info("person={}", person);
		assertThat(person, equalTo(p));
	}

	/** Datensatz aus {@linkplain #b_testInsert()} aus der Liste aller Datensätze finden */
	@Test
	public void e_testeFindAll() {
		List<PersonImpl> personen = (List<PersonImpl>) dao.findAll();
		LOGGER.info("personen={}", personen);
		assertThat(personen, contains(p));
	}

	/** Person mit der {@linkplain #id} wieder löschen darf keine Exception werfen */
	@Test
	public void x_testeLoeschen() {
		dao.delete(id);
	}

}
