package com.github.mbeier1406.adressverwaltung.jee;

public interface DBService<T> {

	void persist(T p);

}