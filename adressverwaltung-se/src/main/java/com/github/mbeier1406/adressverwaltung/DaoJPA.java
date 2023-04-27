package com.github.mbeier1406.adressverwaltung;

import static java.util.Objects.requireNonNull;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.mbeier1406.adressverwaltung.model.PersonImpl;

/**
 * Standard JPA-implementierung.
 * 
 * @author mbeier
 * @param <T> Entity z. B. {@linkplain PersonImpl}
 */
public class DaoJPA<T> implements Dao<T> {

	private static final Logger LOGGER = LogManager.getLogger(DaoJPA.class);

	/** Name der Persistenzeinheit ist {@value} */
	private static final String ADRESSVERWALTUNG = "adressverwaltung";

	private final EntityManagerFactory emf;
	private final EntityManager em;
	private final Class<T> c;

	public DaoJPA(Class<T> t) {
		emf = Persistence.createEntityManagerFactory(ADRESSVERWALTUNG);
		em = emf.createEntityManager();
		c = (Class<T>) requireNonNull(t, "t ist Null!");
	}

	/** {@inheritDoc} */
	@Override
	public void shutdown() {
		em.close();
		emf.close();
	}

	/** {@inheritDoc} */
	@Override
	public PersistenceUnitUtil getPersistenceUnitUtil() {
		return emf.getPersistenceUnitUtil();
	}

	/** {@inheritDoc} */
	@Override
	public T findById(long id) {
		return em.find(c, id);
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<T> findAll() {
		Query query = em.createQuery("SELECT x FROM " + c.getSimpleName() + " x");
		return (Collection<T>) query.getResultList();
	}

	/** {@inheritDoc} */
	@Override
	public T findByProperty(String property, String value) throws IllegalArgumentException {
		return (T) em
				.createQuery("select p from " + c.getSimpleName() + " p where p." + property + " = :x", c)
				.setParameter("x", value)
				.getSingleResult();
	}

	/** {@inheritDoc} */
	@Override
	public void persist(T t) {
		try {
			em.getTransaction().begin();
			em.persist(t);
			em.getTransaction().commit();
		} catch (Exception e) {
			LOGGER.debug("t={}", t, e);
			em.getTransaction().rollback();
			throw e;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void delete(long id) {
		try {
			em.getTransaction().begin();
			T t = em.getReference(c, id);
			em.remove(t);
			em.getTransaction().commit();
		} catch (Exception e) {
			LOGGER.debug("id={}", id, e);
			em.getTransaction().rollback();
			throw e;
		}
	}

	@Override
	public String toString() {
		return "DaoJPA [c=" + c + "]";
	}

}
