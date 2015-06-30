package com.eleks.utils.xml;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateXmlJavaTypeAdapter extends XmlAdapter<String, LocalDate> {
	
	public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

	@Override
	public LocalDate unmarshal(String v) throws Exception {
		return v != null ? LocalDate.parse(v, FORMATTER) : null;
	}

	@Override
	public String marshal(LocalDate v) throws Exception {
		return v != null ? v.format(FORMATTER) : null;
	}

}
