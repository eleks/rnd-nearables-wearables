package com.eleks.utils.json;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class LocalDateSerializer extends JsonSerializer<LocalDate> {
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Override
	public void serialize(LocalDate date, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException, JsonProcessingException {
		jsonGenerator.writeString(date.format(FORMATTER));
	}

}
