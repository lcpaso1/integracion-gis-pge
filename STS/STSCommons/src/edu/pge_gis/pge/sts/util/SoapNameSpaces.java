package edu.pge_gis.pge.sts.util;

import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;

public class SoapNameSpaces implements NamespaceContext {

    

    @Override
    public String getNamespaceURI(String prefix) {
        String uri;
        if (prefix.equals("mins1")) {
            uri = "http://schemas.xmlsoap.org/soap/envelope/";
        } else if (prefix.equals("mins2")) {
            uri = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
        } else if (prefix.equals("mins3")) {
            uri = "http://www.w3.org/2005/08/addressing";
        } else if (prefix.equals("mins4")) {
            uri = "urn:oasis:names:tc:SAML:1.0:assertion";
        } else if (prefix.equals("mins5")) {
            uri = "urn:oasis:names:tc:SAML:2.0:assertion";
        } else if (prefix.equals("pgradns")) {
            uri = "http://fing.edu.uy/p2012_0050";
        } else if (prefix.equals("ds")) {
            uri = "http://www.w3.org/2000/09/xmldsig#";
        } else if (prefix.equals("wsdl")) {
            uri = "http://schemas.xmlsoap.org/wsdl/";
        } else if (prefix.equals("soap")) {
            uri = "http://schemas.xmlsoap.org/wsdl/soap/";
        } else if (prefix.equals("bpel")) {
            uri = "http://docs.oasis-open.org/wsbpel/2.0/process/executable";
        } else {
            uri = null;
        }
        return uri;
    }

    @Override
    public String getPrefix(String namespaceURI) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Iterator getPrefixes(String namespaceURI) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
