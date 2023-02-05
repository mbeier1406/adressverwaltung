package com.github.mbeier1406.adressverwaltung.jee;

import static java.util.Objects.requireNonNull;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.github.mbeier1406.adressverwaltung.jee.model.Person;

@Stateless
public class DBServiceImpl<T> implements DBService<T> {

	@PersistenceContext(unitName = "adressverwaltung")
	private EntityManager em;

	/** Der Typ des Entity (z. B. {@linkplain Person}) */
	private Class<T> c;

	public void setType(final Class<T> c) {
		this.c = (Class<T>) requireNonNull(c, "c ist Null!");
	}

	/** {@inheritDoc} */
	@Override
	public void persist(final T t) {
		em.persist(t);
	}

	/** {@inheritDoc} */
	@Override
	public T findById(long id) {
		return em.find(c, id);
	}

	/** {@inheritDoc} */
	@Override
	public T findByProperty(String property, String value) throws IllegalArgumentException {
		return (T) em.createQuery("select p from " + c.getSimpleName() + " p where p." + property + " = :x", c)
	    	     .setParameter("x", value)
	    	     .getSingleResult();
	}

}
