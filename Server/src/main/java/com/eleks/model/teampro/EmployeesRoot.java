package com.eleks.model.teampro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlPath;

import com.eleks.utils.Constants;
import com.eleks.utils.Constants.EmployeeConstants;

@XmlRootElement(name = Constants.Xml.ROOT)
@XmlAccessorType(XmlAccessType.FIELD)
public class EmployeesRoot {
	
	@XmlElement(name = Constants.Xml.METHOD_RESPONSE)
	private EmployeesMethodResponse employeesMethodResponse;
	
	@XmlAccessorType(XmlAccessType.FIELD)
	private static final class EmployeesMethodResponse {
		
		@XmlPath(Constants.Xml.XML + "[@" + Constants.Xml.NAME + "='" + Constants.Xml.EMPLOYEES_LIST + "']")
		private EmployeesXml employeesXml;
		
		@XmlPath(Constants.Xml.XML + "[@" + Constants.Xml.NAME + "='" + Constants.Xml.EMPLOYEES_EXTRA_DATA + "']")
		private void setEmployeesExtraDataXml(EmployeesExtraDataXml employeesExtraDataXml) {
			final List<Employee> employees = employeesXml.employeesData.employees;
			final Map<Long, Map<Integer, String>> employeesAttributes = employeesExtraDataXml.employeesExtraData.getAttributes();
			
			for(Employee employee : employees) {
				final Map<Integer, String> employeeAttributes = employeesAttributes.get(employee.getId());
				if(employeeAttributes != null && employeeAttributes.size() > 0) {
					employee.setRoomNumber(employeeAttributes.get(EmployeeConstants.ROOM_ATTR_ID));
					employee.setPhoneNumber(employeeAttributes.get(EmployeeConstants.PHONE_ATTR_ID));
					employee.setCarModel(employeeAttributes.get(EmployeeConstants.CAR_MODEL_ATTR_ID));
					employee.setCarNumber(employeeAttributes.get(EmployeeConstants.CAR_NUMBER_ATTR_ID));
					employee.setSex(employeeAttributes.get(EmployeeConstants.SEX));
					
					final String birthdayDay = employeeAttributes.get(EmployeeConstants.BIRTHDAY_DAY);
					final String birthdayMonth = employeeAttributes.get(EmployeeConstants.BIRTHDAY_MONTH);
					if(birthdayDay != null && birthdayMonth != null) {
						employee.setBirthday(Integer.valueOf(birthdayDay), Integer.valueOf(birthdayMonth));
					}
				}
			}
		}
	}
	
	@XmlAccessorType(XmlAccessType.FIELD)
	private static final class EmployeesXml {
		
		@XmlElement(name = Constants.Xml.DATA)
		private EmployeesData employeesData;

	}
	
	@XmlAccessorType(XmlAccessType.FIELD)
	private static final class EmployeesData {
		
		@XmlElement(name = Constants.Xml.ROW)
		private List<Employee> employees = new ArrayList<Employee>();

	}
	
	@XmlAccessorType(XmlAccessType.FIELD)
	private static final class EmployeesExtraDataXml {
		
		@XmlElement(name = Constants.Xml.DATA)
		private EmployeesExtraData employeesExtraData;

	}
	
	@XmlAccessorType(XmlAccessType.FIELD)
	private static final class EmployeesExtraData {
		
		@XmlElement(name = Constants.Xml.ROW)
		private List<EmployeeExtraData> employeesExtraData = new ArrayList<EmployeeExtraData>();
		
		public Map<Long, Map<Integer, String>> getAttributes() {
			final Map<Long, Map<Integer, String>> attributes = new HashMap<Long, Map<Integer, String>>();
			
			for(EmployeeExtraData employeeExtraData : employeesExtraData) {
				Map<Integer, String> map = attributes.get(employeeExtraData.getEmployeeId());
				if(map == null) {
					map = new HashMap<Integer, String>();
					attributes.put(employeeExtraData.getEmployeeId(), map);
				}
				
				if(employeeExtraData.getDataString() != null) {
					map.put(employeeExtraData.getAttributeId(), employeeExtraData.getDataString());
				}
				if(employeeExtraData.getDataInt() != null) {
					map.put(employeeExtraData.getAttributeId(), employeeExtraData.getDataInt());
				}
				if(employeeExtraData.getDataDict() != null) {
					map.put(employeeExtraData.getAttributeId(), employeeExtraData.getDataDict());
				}
			}
			
			return attributes;
		}

	}
	
	public List<Employee> getEmployees() {
		return employeesMethodResponse.employeesXml.employeesData.employees;
	}
}
