package com.eleks.utils.xml;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class ReportTimeXmlJavaTypeAdapter extends XmlAdapter<String, Long> {
	
	public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

	@Override
	public Long unmarshal(String v) throws Exception {
		if(v == null) {
			return 0l;
		}
		
		final LocalTime time = LocalTime.parse(v, FORMATTER);
		final Duration duration = Duration.between(LocalTime.of(0, 0, 0), time);
		
		return duration.getSeconds();
	}

	@Override
	public String marshal(Long v) throws Exception {
		return LocalDateTime.of(1900, 1, 1, 0, 0, 0).plusSeconds(v).format(FORMATTER);
	}

}
