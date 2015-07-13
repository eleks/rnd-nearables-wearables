package com.eleks.model.db;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

@Entity
public class Movement {

	public enum MovementType {
		IN_RANGE, OUT_OF_RANGE
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	private Nearable nearable;

	private long timestamp;

	@ManyToOne(cascade = CascadeType.ALL)
	private User user;

	@Enumerated(EnumType.STRING)
	private MovementType type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Nearable getNearable() {
		return nearable;
	}

	public void setNearable(Nearable nearable) {
		this.nearable = nearable;
	}

	public MovementType getType() {
		return type;
	}

	public void setType(MovementType type) {
		this.type = type;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Movement [id=" + id + ", nearable=" + nearable + ", timestamp=" + timestamp + ", user=" + user + ", type=" + type + "]";
	}

}
