package com.elesyser.util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CurriculumSaxHandler extends DefaultHandler {
	boolean findSpan = false;
	
	@Override
	public void startElement(String uri, String localName, String qName,
		Attributes attributes) throws SAXException {
		String tagName = localName.length() != 0 ? localName : qName;
		tagName = tagName.toLowerCase().trim();
		if(tagName.equals("span") &&  attributes.getValue("id").endsWith("LessonTbl1_spanContent")){
			 findSpan = true;
		}
		if(tagName.equals("table")){
			System.out.println("fsafsaf");
		}
	}
	
	@Override
	 public void endElement(String uri, String localName, String qName)
	 throws SAXException {
		
	}
}
