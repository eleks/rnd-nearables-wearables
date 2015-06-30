package com.eleks.model.teampro;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlPath;

import com.eleks.utils.Constants;

@XmlRootElement(name = Constants.Xml.ROOT)
@XmlAccessorType(XmlAccessType.FIELD)
public class ReportsRoot {

	@XmlElement(name = Constants.Xml.METHOD_RESPONSE)
	private ReportsMethodResponse employeesMethodResponse;
	
	@XmlAccessorType(XmlAccessType.FIELD)
	private static final class ReportsMethodResponse {
		
		@XmlPath(Constants.Xml.XML + "[@" + Constants.Xml.NAME + "='" + Constants.Xml.REPORTS_LIST + "']")
		private ReportsXml reportsXml;
		
	}
	
	@XmlAccessorType(XmlAccessType.FIELD)
	private static final class ReportsXml {
		
		@XmlElement(name = Constants.Xml.DATA)
		private ReportsData reportsData;

	}
	
	@XmlAccessorType(XmlAccessType.FIELD)
	private static final class ReportsData {
		
		@XmlElement(name = Constants.Xml.ROW)
		private List<Report> reports = new ArrayList<Report>();

	}
	
	public List<Report> getReports() {
		return employeesMethodResponse.reportsXml.reportsData.reports;
	}
	
}
