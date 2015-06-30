package com.eleks.utils.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.eleks.utils.Constants;

public class BooleanXmlJavaTypeAdapter extends XmlAdapter<String, Boolean> {

	@Override
	public Boolean unmarshal(String v) throws Exception {
		return Constants.TRUE.equals(v);
	}

	@Override
	public String marshal(Boolean v) throws Exception {
		return (v != null && v == true) ? Constants.TRUE : Constants.FALSE;
	}

}
