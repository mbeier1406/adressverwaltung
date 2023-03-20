package com.github.mbeier1406.adressverwaltung.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Entity implementation class for Entity: Adresse
 */
@Entity
public class Adresse {

	@Id
	@GeneratedValue
	private long id;
	private int plz;
	private String ort;
	private String strasseUndHausnummer;

	public Adresse() {
		super();
	}

	public Adresse(int plz, String ort, String strasseUndHausnummer) {
		super();
		this.plz = plz;
		this.ort = ort;
		this.strasseUndHausnummer = strasseUndHausnummer;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getPlz() {
		return plz;
	}

	public void setPlz(int plz) {
		this.plz = plz;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	public String getStrasseUndHausnummer() {
		return strasseUndHausnummer;
	}

	public void setStrasseUndHausnummer(String strasseUndHausnummer) {
		this.strasseUndHausnummer = strasseUndHausnummer;
	}

	@Override
	public String toString() {
		return "Adresse [id=" + id + ", plz=" + plz + ", ort=" + ort + ", strasseUndHausnummer=" + strasseUndHausnummer
				+ "]";
	}

}
