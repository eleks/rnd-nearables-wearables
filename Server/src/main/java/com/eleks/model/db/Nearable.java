package com.eleks.model.db;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Nearable implements Serializable {
	private static final Map<String, String> uuids = new HashMap<String, String>();

	static {
		uuids.put("b4aaa62e03d1dcef", "Dog");
		uuids.put("e59b0d69306fab9f", "Chair");
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// index neeeded
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Nearable [id=" + id + ", uid=" + uid + ", name=" + name + "]";
	}

	public static class Builder {
		private String uuid;
		private String name;

		public Builder withUuid(String uuid) {
			this.uuid = uuid;
			String title = uuids.get(uuid);
			if (title == null) {
				title = "Unknown Place";
			}
			this.name = title;
			return this;
		}

		public Nearable build() {
			Nearable n = new Nearable();
			n.setName(name);
			n.setUid(uuid);
			return n;
		}
	}

}
