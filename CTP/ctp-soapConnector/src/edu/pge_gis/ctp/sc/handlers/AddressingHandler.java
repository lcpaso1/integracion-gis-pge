package edu.pge_gis.ctp.sc.handlers;


import javax.xml.namespace.QName;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class AddressingHandler implements Handler<SOAPMessageContext> {

	private String accion = "prueba_instancia";
	
	//se encarga de tomar los cabezales de addressing en el pediddo y agregarlo en la respuesta
	public boolean handleMessage(SOAPMessageContext context) {
		// TODO Auto-generated method stub
		SOAPMessage soapMessage =  context.getMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();
		SOAPEnvelope soapEnvelope;
		try {
			Boolean outboundProperty = (Boolean)
			         context.get (MessageContext.MESSAGE_OUTBOUND_PROPERTY);
			soapEnvelope = soapPart.getEnvelope();
			SOAPHeader soapHeader = soapEnvelope.getHeader();
			QName wsaActionQName = new QName("http://www.w3.org/2005/08/addressing", "Action", "wsa");
			//si es de salida, le pongo el cabezal action de addressing para que no joda el cliente
			if (outboundProperty.booleanValue()) {
				soapHeader.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:wsa", "http://www.w3.org/2005/08/addressing");//;addAttribute(naddr, "http://www.w3.org/2005/08/addressing");
				
	            SOAPHeaderElement wsaAction =  soapHeader.addHeaderElement(wsaActionQName);
	            wsaAction.addTextNode(accion+"_Response");
			}
			else{
				//si es de entrada leo el cabezal action y lo guardo en la instancia
				//System.out.println(soapHeader.getChildElements(wsaActionQName).next().getClass());
				SOAPHeaderElement wsaAction = (SOAPHeaderElement)soapHeader.getChildElements(wsaActionQName).next();
				accion = wsaAction.getValue();
			}
		} catch (SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//SOAPBody soapBody = soapEnvelope.getBody();
		 
		return true;
	}

	public boolean handleFault(SOAPMessageContext context) {
		// TODO Auto-generated method stub
		return false;
	}

	public void close(MessageContext context) {
		// TODO Auto-generated method stub
		
	}

}
