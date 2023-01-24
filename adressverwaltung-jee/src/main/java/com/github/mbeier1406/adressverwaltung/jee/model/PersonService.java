package com.github.mbeier1406.adressverwaltung.jee.model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PersonService {

	@PersistenceContext(unitName = "adressverwaltung")
	private EntityManager em;

	public void persist(final Person p) {
		em.persist(p);
	}

}
