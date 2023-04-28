package com.github.mbeier1406.adressverwaltung.model;

import static com.github.mbeier1406.adressverwaltung.model.Person.Geschlecht.MAENNLICH;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

	/** Zum Prüfen, ob Adressen vorhanden sind */
	public static Dao<Adresse> daoAdr;

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

	/** DAO initialisieren, DB leeren */
	@BeforeClass
	public static void init() {
		LOGGER.info("DB verbinden...");
		dao = new DaoJPA<>(PersonImpl.class);
		daoAdr = new DaoJPA<>(Adresse.class);
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

	/** Adressliste muss jetzt einen Datensatz enthalten */
	@Test
	public void b2_testeAdresseWurdeMitGespeichert() {
		final var adr = daoAdr.findByProperty("ort", "Ort");
		assertThat(adr, not(equalTo(Optional.empty())));
		LOGGER.info("adr={}", adr.get());
	}

	/** Datensatz anhand der in {@linkplain #b_testInsert()} vergebenen ID aus {@linkplain #p} wieder einlesen */
	@Test
	public void c_testeFindById() {
		final var person = dao.findById(p.getId());
		assertThat(person, not(equalTo(Optional.empty())));
		LOGGER.info("person={}", person.get().toInfo());
		assertThat(dao.getPersistenceUnitUtil().getIdentifier(person.get()), equalTo(p.getId()));
		assertThat(dao.getPersistenceUnitUtil().isLoaded(person.get()), equalTo(true));
		assertThat(dao.getPersistenceUnitUtil().isLoaded(person.get(), "vorname"), equalTo(true));
		assertThat(p, equalTo(person.get()));
	}

	/** Datensatz anhand eines der in {@linkplain #b_testInsert()} vergebenen Proprties wieder einlesen */
	@Test
	public void d_testeFindByProperty() {
		final var person = dao.findByProperty("vorname", "Kulle");
		assertThat(person, not(equalTo(Optional.empty())));
		LOGGER.info("person={}", person.get().toInfo());
		assertThat(person.get(), equalTo(p));
	}

	/** Datensatz aus {@linkplain #b_testInsert()} aus der Liste aller Datensätze finden */
	@Test
	public void e_testeFindAll() {
		final var personen = (List<PersonImpl>) dao.findAll();
		LOGGER.info("personen={}", personen);
		assertThat(personen, hasItem(p));
	}

	/** Person mit der ID von {@linkplain #p} wieder löschen: darf keine Exception werfen */
	@Test
	public void f_testeLoeschen() {
		dao.delete(p.getId());
	}

	/** Adressliste muss jetzt leer sein, da mit gelöscht */
	@Test
	public void g_testeAdresseWurdeMitGeloescht() {
		final var adr = daoAdr.findByProperty("ort", "Ort");
		assertThat(adr, equalTo(Optional.empty()));
	}

	/** Testet das automatische Löschen von alten Adressen wenn {@linkplain #p} eine neue erhält */
	@Test
	public void h_testeOrphanRemoval() {
		p.setId(0); // Objekt ist detached!
		var a = new Adresse(1234, "Ort", "Strasse", p);
		p.setAdresse(a);
		dao.persist(p);
		var adr = daoAdr.findByProperty("ort", "Ort");
		assertThat(adr, not(equalTo(Optional.empty())));
		a = new Adresse(1235, "Ort2", "Strasse2", p);
		p.setAdresse(a);
		dao.persist(p);
		adr = daoAdr.findByProperty("ort", "Ort");
		assertThat(adr, equalTo(Optional.empty()));
		adr = daoAdr.findByProperty("ort", "Ort2");
		assertThat(adr, not(equalTo(Optional.empty())));
	}

}
