
package org.tempuri;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.8
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "NewSTSWizard", targetNamespace = "http://tempuri.org/", wsdlLocation = "file:/C:/ProyectoDeGrado/STS/STSServer/src/conf/xml-resources/web-services/NewSTSWizard/wsdl/NewSTSWizard.wsdl")
public class NewSTSWizard
    extends Service
{

    private final static URL NEWSTSWIZARD_WSDL_LOCATION;
    private final static WebServiceException NEWSTSWIZARD_EXCEPTION;
    private final static QName NEWSTSWIZARD_QNAME = new QName("http://tempuri.org/", "NewSTSWizard");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/C:/ProyectoDeGrado/STS/STSServer/src/conf/xml-resources/web-services/NewSTSWizard/wsdl/NewSTSWizard.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        NEWSTSWIZARD_WSDL_LOCATION = url;
        NEWSTSWIZARD_EXCEPTION = e;
    }

    public NewSTSWizard() {
        super(__getWsdlLocation(), NEWSTSWIZARD_QNAME);
    }

    public NewSTSWizard(WebServiceFeature... features) {
        super(__getWsdlLocation(), NEWSTSWIZARD_QNAME, features);
    }

    public NewSTSWizard(URL wsdlLocation) {
        super(wsdlLocation, NEWSTSWIZARD_QNAME);
    }

    public NewSTSWizard(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, NEWSTSWIZARD_QNAME, features);
    }

    public NewSTSWizard(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public NewSTSWizard(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    private static URL __getWsdlLocation() {
        if (NEWSTSWIZARD_EXCEPTION!= null) {
            throw NEWSTSWIZARD_EXCEPTION;
        }
        return NEWSTSWIZARD_WSDL_LOCATION;
    }

}