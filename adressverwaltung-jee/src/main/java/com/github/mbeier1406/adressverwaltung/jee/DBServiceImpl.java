package com.github.mbeier1406.adressverwaltung.jee;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class DBServiceImpl<T> implements DBService<T> {

	@PersistenceContext(unitName = "adressverwaltung")
	private EntityManager em;

	@Override
	public void persist(final T t) {
		em.persist(t);
	}

}
