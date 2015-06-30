package com.eleks.model.db;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Nearable {
	
	@Id
	private String uid;
	
	private String name;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
