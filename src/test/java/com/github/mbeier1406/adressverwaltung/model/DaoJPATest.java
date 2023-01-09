package com.github.mbeier1406.adressverwaltung.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	public static Dao<Person> dao;

	public static long id = 0;

	/** DAO initialisieren */
	@BeforeClass
	public static void init() {
		dao = new DaoJPA<>(new Person());
	}

	/** Stellt sicher, dass die Klasse im Generic korrekt übernommen wird */
	@Test
	public void a_testeToString() {
		LOGGER.info("dao={}", dao);
		assertThat(dao.toString(), equalTo("DaoJPA [c=class com.github.mbeier1406.adressverwaltung.model.Person]"));
	}

	/** Fügt einen Datensatz ein, der später abgefragt wird */
	@Test
	public void b_testInsert() {
		final var p = new Person("Kulle", "Wolters");
		dao.persist(p);
		LOGGER.info("id={}", id=p.getId());
	}

}
