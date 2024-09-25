package com.maybank.integratorapp.util;

import javax.xml.namespace.QName;
import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPException;
//import javax.xml.soap.SOAPElement;
//import javax.xml.soap.SOAPException;


public class SOAPUtil { 
	
	public SOAPElement setProperties(SOAPElement parent,String name,String prefix,String value,String uri) throws SOAPException {
		QName messageID = new QName(uri,name,prefix);
		SOAPElement se = parent.addChildElement(messageID);
		se.setTextContent(value);
		return se; 
	}
	
	 
	
}
