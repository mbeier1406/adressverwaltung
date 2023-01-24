package com.github.mbeier1406.adressverwaltung.jee.model;

import java.util.Date;

/**
 * Definiert das Interface zur Klasse einer Person.
 * @author mbeier
 *
 */
public interface Person {

	/**	Zur Demonstartion der <code>@enumerated</code> Annotation */
	public enum Geschlecht { WEIBLICH, MAENNLICH }

	/**
	 * Liefert den Vornamen.
	 * @return den Vornamen
	 */
	public String getVorname();

	/**
	 * Setzt den Vornamen.
	 * @param vorname der Vorname
	 */
	public void setVorname(final String vorname);

	/**
	 * Liefert den Nachnamen.
	 * @return den Nachnamen
	 */
	public String getNachname();

	/**
	 * Setzt den Nachnamen.
	 * @param nachname der Nachnamen
	 */
	public void setNachname(final String nachname);

	/**
	 * Liefert das Geburtsdatum.
	 * @return das Geburtsdatum
	 */
	public Date getGeburtsdatum();

	/**
	 * Setzt das Geburtsdatum.
	 * @param geburtsdatum das Geburtsdatum
	 */
	public void setGeburtsdatum(final Date geburtsdatum);

	/**
	 * Liefert das Geschlecht.
	 * @return das Geschlecht
	 */
	public Geschlecht getGeschlecht();

	/**
	 * Setzt das Geschlecht.
	 * @param geschlecht das Geschlecht
	 */
	public void setGeschlecht(final Geschlecht geschlecht);

	/**
	 * Liefert das Passbild.
	 * @return das Passbild
	 */
	public byte[] getPassbild();

	/**
	 * Setzt das Passbild.
	 * @param passbild das Passbild
	 */
	public void setPassbild(final byte[] passbild);

}