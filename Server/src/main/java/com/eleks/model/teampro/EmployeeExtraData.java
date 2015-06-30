package com.eleks.model.teampro;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import static com.eleks.utils.Constants.EmployeeConstants.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeeExtraData {

	@XmlAttribute(name = USER_REF)
	private long employeeId;

	@XmlAttribute(name = ATTR_ID)
	private int attributeId;

	@XmlAttribute(name = DATA_STR)
	private String dataString;
	
	@XmlAttribute(name = DATA_INT)
	private String dataInt;
	
	@XmlAttribute(name = DATA_DICT)
	private String dataDict;
	
	public long getEmployeeId() {
		return employeeId;
	}

	public int getAttributeId() {
		return attributeId;
	}
	
	public String getDataString() {
		return dataString;
	}

	public String getDataInt() {
		return dataInt;
	}

	public String getDataDict() {
		return dataDict;
	}

}
