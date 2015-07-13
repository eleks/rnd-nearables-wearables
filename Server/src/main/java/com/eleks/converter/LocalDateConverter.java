package com.eleks.converter;

import java.sql.Date;

import javax.persistence.AttributeConverter;

import org.joda.time.LocalDate;

public class LocalDateConverter implements AttributeConverter<LocalDate, Date>{

	@Override
	public Date convertToDatabaseColumn(LocalDate localDate) {
		if(localDate != null) {
			return new Date(localDate.toDate().getTime());
		}
		
		return null;
	}

	@Override
	public LocalDate convertToEntityAttribute(Date date) {
		if(date != null) {
			return new LocalDate(date.getTime());
		}
		
		return null;
	}

}
