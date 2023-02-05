package com.github.mbeier1406.adressverwaltung.jee.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Entity implementation class for Entity: Person
 */
@EntityListeners({ PersonListener.class })
@Entity
@Table(name="person")
public class Person implements Serializable {

	/** Interne Bezeichnung für die Konfig der Sequenz ist {@value} */
	private static final String PERSONEN_SEQUENZ = "personen_sequenz";

	/**	Zur Demonstartion der <code>@enumerated</code> Annotation */
	public enum Geschlecht { WEIBLICH, MAENNLICH }

	private long id;
	private String vorname;
	private String nachname;
	private Date geburtsdatum;
	private Geschlecht geschlecht;
	private byte[] passbild;
	private char[] daten;
	private static final long serialVersionUID = 1L;

	public Person() {
		super();
	}

	public Person(String vorname, String nachname, Date geburtsdatum, Geschlecht geschlecht, byte[] passbild) {
		super();
		this.vorname = vorname;
		this.nachname = nachname;
		this.geburtsdatum = geburtsdatum;
		this.geschlecht = geschlecht;
		this.passbild = passbild;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = PERSONEN_SEQUENZ)
	@SequenceGenerator(name = PERSONEN_SEQUENZ, sequenceName = "personen_seq", initialValue = 1000)
	@Column(name="id_person")
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/** {@inheritDoc} */
	public String getVorname() {
		return this.vorname;
	}

	/** {@inheritDoc} */
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	/** {@inheritDoc} */
	@Temporal(TemporalType.DATE)
	public Date getGeburtsdatum() {
		return geburtsdatum;
	}

	/** {@inheritDoc} */
	public void setGeburtsdatum(Date geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}

	/** {@inheritDoc} */
	public String getNachname() {
		return this.nachname;
	}

	/** {@inheritDoc} */
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	/** {@inheritDoc} */
	@Enumerated(EnumType.STRING)
	public Geschlecht getGeschlecht() {
		return geschlecht;
	}

	/** {@inheritDoc} */
	public void setGeschlecht(Geschlecht geschlecht) {
		this.geschlecht = geschlecht;
	}

	/** {@inheritDoc} */
	@Lob
	public byte[] getPassbild() {
		return this.passbild;
	}

	/** {@inheritDoc} */
	public void setPassbild(byte[] passbild) {
		this.passbild = passbild;
	}

	@Transient // nicht persistieren
	public char[] getDaten() {
		return daten;
	}

	public void setDaten(char[] daten) {
		this.daten = daten;
	}

	@Override
	public int hashCode() {
		return Objects.hash(geburtsdatum, geschlecht, nachname, vorname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		return Objects.equals(geburtsdatum, other.geburtsdatum) && geschlecht == other.geschlecht
				&& Objects.equals(nachname, other.nachname) && Objects.equals(vorname, other.vorname);
	}

	@Override
	public String toString() {
		return "PersonImpl [id=" + id + ", vorname=" + vorname + ", nachname=" + nachname + ", geburtsdatum="
				+ geburtsdatum + ", geschlecht=" + geschlecht + "]";
	}

}
