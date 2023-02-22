package com.github.mbeier1406.adressverwaltung.jee;

/**
 * Definiert das Interface zum DB-Service.
 * @author mbeier
 * TODO: fehlende CRUD-Methoden definieren
 * @param <T>
 */
public interface DBService<T> {

	public void setType(final Class<T> c);

	/**
	 * Setzt das Entity {@linkplain T} in den Zustand "managed".
	 * @param t das zu speichernde Objekt
	 */
	public void persist(final T p);

	/**
	 * Sucht das Objekt anhand seiner ID.
	 * @param id die ID des Objekts
	 * @return das Objekt oder <b>null</b>, wenn nicht vorhanden
	 */
	public T findById(long id);

	/**
	 * Liefert das Objekt zur Suchanfrage.
	 * @param property Key
	 * @param value Wert
	 * @return das gefundene Objekt oder <b>null</b>, wenn nicht vorhanden
	 * @throws IllegalArgumentException falls es mehr als einen Treffer gibt
	 */
	public T findByProperty(final String property, final String value);

}