package com.github.mbeier1406.adressverwaltung.jee.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

/**
 * Protokolliert JPA-Aktionen mit der Entity {@linkplain PersonImpl}.
 * @author mbeier
 * @see Person
 */
public class PersonListener {

	/** Das Verzeichnis, in das die Protokolldateien geschrieben werden, ist {@value} */
	private static final String TMP_DIR = System.getProperty("java.io.tmpdir")+File.separator;

	@PrePersist
	public void beforePersisting(Person p) {
		logEntity("vp", p);
	}

	@PreUpdate
	public void beforeUpdating(Person p) {
		logEntity("vu", p);
	}

	@PreRemove
	public void beforeRemoving(Person p) {
		logEntity("vr", p);
	}

	@PostPersist
	@PostUpdate
	@PostRemove
	public void afterRemoving(Person p) {
		logEntity("n", p);
	}

	@PostLoad
	public void afterLoading(Person p) {
		logEntity("nl", p);
	}

	private void logEntity(String name, Person p) {
		try {
			Files.writeString(Paths.get(TMP_DIR+name+"_"+((PersonImpl) p).getId()+".txt"), p.toString(), StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

}
