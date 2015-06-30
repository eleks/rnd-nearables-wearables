package com.eleks.model.teampro;

import java.time.LocalDate;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.eleks.utils.json.LocalDateDeserializer;
import com.eleks.utils.json.LocalDateSerializer;
import com.eleks.utils.xml.LocalDateXmlJavaTypeAdapter;
import com.eleks.utils.xml.ReportTimeXmlJavaTypeAdapter;
import com.eleks.utils.xml.BooleanXmlJavaTypeAdapter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import static com.eleks.utils.Constants.ReportConstants.*;

public class Report {

	@XmlAttribute(name = WORK_TIME_DATE)
	@XmlJavaTypeAdapter(value = LocalDateXmlJavaTypeAdapter.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate workDate;
	
	@XmlAttribute(name = WORK_TIME_NAME)
	private String description;
	
	@XmlAttribute(name = WORK_TIME_USER_REF)
	private String userRef;
	
	@XmlAttribute(name = PROJECT_PATH)
	private String project;
	
	@XmlAttribute(name = TYPE_NAME)
	private String workType;
	
	@XmlAttribute(name = WORK_TIME_STATUS_NAME)
	private String status;
	
	@XmlAttribute(name = VALID)
	@XmlJavaTypeAdapter(value = BooleanXmlJavaTypeAdapter.class)
	private boolean isValid;
	
	@XmlAttribute(name = WORK_TIME_TIME)
	@XmlJavaTypeAdapter(value = ReportTimeXmlJavaTypeAdapter.class)
	private long workTime;

	public LocalDate getWorkDate() {
		return workDate;
	}

	public String getDescription() {
		return description;
	}

	public String getUserRef() {
		return userRef;
	}

	public String getProject() {
		return project;
	}

	public String getWorkType() {
		return workType;
	}

	public String getStatus() {
		return status;
	}

	public boolean isValid() {
		return isValid;
	}

	public long getWorkTime() {
		return workTime;
	}
}
