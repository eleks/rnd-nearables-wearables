package com.eleks.model.teampro;

import static com.eleks.utils.Constants.EmployeeConstants.DEPARTMENT;
import static com.eleks.utils.Constants.EmployeeConstants.DIVISION;
import static com.eleks.utils.Constants.EmployeeConstants.EMAIL;
import static com.eleks.utils.Constants.EmployeeConstants.FULL_NAME;
import static com.eleks.utils.Constants.EmployeeConstants.ID;
import static com.eleks.utils.Constants.EmployeeConstants.INV_POSITION;
import static com.eleks.utils.Constants.EmployeeConstants.INV_PROJECT_PATH;
import static com.eleks.utils.Constants.EmployeeConstants.INV_SUPERIOR;
import static com.eleks.utils.Constants.EmployeeConstants.JOB_TITLE;
import static com.eleks.utils.Constants.EmployeeConstants.JOB_TITLE_AREA;
import static com.eleks.utils.Constants.EmployeeConstants.KIND_1;
import static com.eleks.utils.Constants.EmployeeConstants.KIND_2;
import static com.eleks.utils.Constants.EmployeeConstants.LEVEL_1;
import static com.eleks.utils.Constants.EmployeeConstants.LEVEL_2;
import static com.eleks.utils.Constants.EmployeeConstants.OFFICE;
import static com.eleks.utils.Constants.EmployeeConstants.SKYPE;
import static com.eleks.utils.Constants.EmployeeConstants.LOGIN;

import java.time.LocalDate;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import com.eleks.converter.LocalDateConverter;
import com.eleks.utils.json.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(indexes = {
		@Index(name="email_index", columnList = "email")
})
@XmlAccessorType(XmlAccessType.FIELD)
public class Employee {

	@Id
	@XmlAttribute(name = ID)
	private long id;
	
	private String login;
	
	@XmlAttribute(name = FULL_NAME)
	private String fullName;
	
	@XmlAttribute(name = DEPARTMENT)
	private String department;
	
	@XmlAttribute(name = LEVEL_1)
	private String level1;
	
	@XmlAttribute(name = LEVEL_2)
	private String level2;
	
	@XmlAttribute(name = KIND_1)
	private String kind1;
	
	@XmlAttribute(name = KIND_2)
	private String kind2;
	
	@XmlAttribute(name = EMAIL)
	private String email;
	
	@XmlAttribute(name = INV_POSITION)
	private String involvementPosition;

	@XmlAttribute(name = INV_PROJECT_PATH)
	private String involvementProjectPath;
	
	@XmlAttribute(name = INV_SUPERIOR)
	private String involvementSuperior;
	
	@XmlAttribute(name = DIVISION)
	private String division;
	
	@XmlAttribute(name = JOB_TITLE)
	private String jobTitle;
	
	@XmlAttribute(name = JOB_TITLE_AREA)
	private String jobTitleArea;
	
	private String skype;
	
	@XmlAttribute(name = OFFICE)
	private String office;

	private String roomNumber;
	private String phoneNumber;
	private String carModel;
	private String carNumber;
	private String sex;
	
	@Convert(converter = LocalDateConverter.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate birthday;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}

	@XmlAttribute(name = LOGIN)
	private void setLoginFromXml(String login) {
		String[] parts = login.split("\\\\"); 
		this.login = parts[parts.length > 1 ? 1 : 0];
	}
	
	
	public void setLogin(String login) {
		this.login = login;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public String getSkype() {
		return skype;
	}

	@XmlAttribute(name = SKYPE)
	private void setSkypeFromXml(String skype) {
		final int start = skype.indexOf(":");
		final int end = skype.indexOf("?");
		this.skype = skype.substring(start != -1 ? start + 1 : 0, end != -1 ? end : 0);
	}
	
	public void setSkype(String skype) {
		this.skype = skype;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getLevel1() {
		return level1;
	}

	public void setLevel1(String level1) {
		this.level1 = level1;
	}

	public String getLevel2() {
		return level2;
	}

	public void setLevel2(String level2) {
		this.level2 = level2;
	}

	public String getKind1() {
		return kind1;
	}

	public void setKind1(String kind1) {
		this.kind1 = kind1;
	}

	public String getKind2() {
		return kind2;
	}

	public void setKind2(String kind2) {
		this.kind2 = kind2;
	}

	public String getInvolvementPosition() {
		return involvementPosition;
	}

	public void setInvolvementPosition(String involvementPosition) {
		this.involvementPosition = involvementPosition;
	}

	public String getInvolvementProjectPath() {
		return involvementProjectPath;
	}

	public void setInvolvementProjectPath(String involvementProjectPath) {
		this.involvementProjectPath = involvementProjectPath;
	}

	public String getInvolvementSuperior() {
		return involvementSuperior;
	}

	public void setInvolvementSuperior(String involvementSuperior) {
		this.involvementSuperior = involvementSuperior;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getJobTitleArea() {
		return jobTitleArea;
	}

	public void setJobTitleArea(String jobTitleArea) {
		this.jobTitleArea = jobTitleArea;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(int day, int month) {
		this.birthday = LocalDate.of(1900, month, day);
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
}
