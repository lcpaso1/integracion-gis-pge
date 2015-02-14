package edu.pge_gis.ctp.sc.handlers;

import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class WSSecurityHandler implements SOAPHandler<SOAPMessageContext> {


	public Set<QName> getHeaders() {
	    final QName securityHeader = new QName(
	        "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd",
	        "Security",
	        "wsse");
	 
	        // ... "understand" the response, very complex logic goes here
	    // ... "understand" the response, very complex logic goes here
	    // ... "understand" the response, very complex logic goes here
	 
	        final HashSet headers = new HashSet();
	        headers.add(securityHeader);
	 
	        // notify the runtime that this is handled
	    return headers;
	}
	
	
	public void close(MessageContext arg0) {
		// TODO Auto-generated method stub
		
	}

	public boolean handleFault(SOAPMessageContext arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean handleMessage(SOAPMessageContext arg0) {
		// TODO Auto-generated method stub
		return true;
	}

}
