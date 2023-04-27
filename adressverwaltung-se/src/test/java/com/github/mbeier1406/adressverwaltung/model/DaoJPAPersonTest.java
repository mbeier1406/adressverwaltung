package com.github.mbeier1406.adressverwaltung.model;

import static com.github.mbeier1406.adressverwaltung.model.Person.Geschlecht.MAENNLICH;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import javax.persistence.PersistenceException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.github.mbeier1406.adressverwaltung.Dao;
import com.github.mbeier1406.adressverwaltung.DaoJPA;

/**
 * Tests für die Klasse {@linkplain DaoJPA}.
 * @author mbeier
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DaoJPAPersonTest {

	public static final Logger LOGGER = LogManager.getLogger(DaoJPAPersonTest.class);

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

	/** Versucht einen Datensatz <b>ohne</b> Adresse einzufügen, die aber nicht optional ist */
	@Test(expected = PersistenceException.class)
	public void b0_testInsert() {
		dao.persist(p);
	}

	/** Fügt einen Datensatz mit Adresse ein, der später abgefragt wird */
	@Test
	public void b1_testInsert() {
		p.setId(0); // Objekt ist nach Aufruf b0_testInsert() detached!
		final var a = new Adresse(1234, "Ort", "Strasse", p);
		p.setAdresse(a);
		dao.persist(p);
		LOGGER.info("id={}", p.getId());
	}

//	/** Datensatz anhand der in {@linkplain #b_testInsert()} vergebenen {@linkplain #id} wieder einlesen */
//	@Test
//	public void c_testeFindById() {
//		final var person = dao.findById(p.getId());
//		LOGGER.info("person={}", person);
//		assertThat(dao.getPersistenceUnitUtil().getIdentifier(p), equalTo(p.getId()));
//		assertThat(dao.getPersistenceUnitUtil().isLoaded(p), equalTo(true));
//		assertThat(dao.getPersistenceUnitUtil().isLoaded(p, "vorname"), equalTo(true));
//		assertThat(person, equalTo(p));
//	}
//
//	/** Datensatz anhand eines der in {@linkplain #b_testInsert()} vergebenen Proprties wieder einlesen */
//	@Test
//	public void d_testeFindByProperty() {
//		final var person = dao.findByProperty("vorname", "Kulle");
//		LOGGER.info("person={}", person);
//		assertThat(person, equalTo(p));
//	}
//
//	/** Datensatz aus {@linkplain #b_testInsert()} aus der Liste aller Datensätze finden */
//	@Test
//	public void e_testeFindAll() {
//		List<PersonImpl> personen = (List<PersonImpl>) dao.findAll();
//		LOGGER.info("personen={}", personen);
//		assertThat(personen, hasItem(p));
//	}
//
//	/** Person mit der {@linkplain #id} wieder löschen darf keine Exception werfen */
//	@Test
//	public void x_testeLoeschen() {
//		dao.delete(p.getId());
//	}
//
//	/** Person mit Adresse speichern */
//	@Test
//	public void z0_testeSpeichernMitAdresse() {
//		PersonImpl person = new PersonImpl("Karl", "mit Adresse", new Date(), MAENNLICH, null);
//		person.setAdresse(new Adresse(11111, "Entenhausen", "Bei Donald", person));
//		dao.persist(person);
//	}
//
//	/** Person mit Adresse cascdierend löschen */
//	@Test
//	public void z1_testeLoeschenMitAdresse() {
//		final var person = dao.findByProperty("nachname", "mit Adresse");
//		LOGGER.info("person={}", person);
//		dao.delete(person.getId());
//	}
//
//	/** Person ohne Adresse speichern erzeugt einen Fehler (Pflichtfeld) */
//	@Test(expected=javax.persistence.PersistenceException.class)
//	public void z2_testeSpeichernMitAdresse() {
//		dao.persist(new PersonImpl("X", "Y", new Date(), MAENNLICH, null));
//	}

}
