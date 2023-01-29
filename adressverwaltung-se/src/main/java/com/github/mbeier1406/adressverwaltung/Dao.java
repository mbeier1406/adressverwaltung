package com.github.mbeier1406.adressverwaltung;

import java.util.Collection;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;

import com.github.mbeier1406.adressverwaltung.model.PersonImpl;

/**
 * Standard DAO-Pattern.
 * @author mbeier
 * @param <T> Entity z. B. {@linkplain PersonImpl}
 */
public interface Dao<T> {

	/** Datenbank schließen */
	public void shutdown();

	/**
	 * Liefert das {@linkplain PersistenceUnitUtil} der {@linkplain EntityManagerFactory}.
	 * @return das PersistenceUnitUtil
	 */
	public PersistenceUnitUtil getPersistenceUnitUtil();

	/**
	 * Sucht das Objekt anhand seiner ID.
	 * @param id die ID des Objekts
	 * @return das Objekt oder <b>null</b>, wenn nicht vorhanden
	 */
	public T findById(long id);

	/**
	 * Liefert alle Objekte des Typs {@linkplain T}.
	 * @return Liste der Objekte, leer wenn kein Treffer
	 */
	public Collection<T> findAll();

	/**
	 * Liefert das Objekt zur Suchanfrage.
	 * @param property Key
	 * @param value Wert
	 * @return das gefundene Objekt oder <b>null</b>, wenn nicht vorhanden
	 * @throws IllegalArgumentException falls es mehr als einen Treffer gibt
	 */
	public T findByProperty(String property, String value) throws IllegalArgumentException;

	/**
	 * Setzt das Entity {@linkplain T} in den Zustand "managed".
	 * @param t das zu speichernde Objekt
	 */
	public void persist(T t);

	/**
	 * Markiert das Objekt zum Löschen. 
	 * @param id ID des zu löschenden Objekts
	 */
	public void delete(long id);

}
