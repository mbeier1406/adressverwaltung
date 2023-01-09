package com.github.mbeier1406.adressverwaltung.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Entity implementation class for Entity: Person
 */
@Entity
public class Person implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private long id;
	private String vorname;
	private String nachname;
	private static final long serialVersionUID = 1L;

	public Person() {
		super();
	}

	public Person(String vorname, String nachname) {
		super();
		this.vorname = vorname;
		this.nachname = nachname;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getVorname() {
		return this.vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return this.nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", vorname=" + vorname + ", nachname=" + nachname + "]";
	}

}