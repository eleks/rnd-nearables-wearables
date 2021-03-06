package com.eleks.model.db;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.eleks.model.teampro.Employee;

@Entity
@Table(name = "Users", indexes = { @Index(name = "user_name_index", columnList = "userName") })
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, unique = true)
	private String userName;

	@Column(nullable = false)
	private String accessToken;

	@OneToOne(cascade = CascadeType.ALL)
	private Employee employee;

	protected User() {
	}

	public User(String userName, String accessToken) {
		this.userName = userName;
		this.accessToken = accessToken;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", accessToken=" + accessToken + "]";
	}
}
