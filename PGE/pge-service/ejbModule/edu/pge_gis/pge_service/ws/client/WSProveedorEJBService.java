
package edu.pge_gis.pge_service.ws.client;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.1-b03-
 * Generated source version: 2.0
 * 
 */
@WebServiceClient(name = "WSProveedorEJBService", targetNamespace = "http://proveedor.pge_service.pge_gis.edu/", wsdlLocation = "http://localhost:8080/proveedorSOAP/WSProveedor?wsdl")
public class WSProveedorEJBService
    extends Service
{

    private final static URL WSPROVEEDOREJBSERVICE_WSDL_LOCATION;

    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/proveedorSOAP/WSProveedor?wsdl");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        WSPROVEEDOREJBSERVICE_WSDL_LOCATION = url;
    }

    public WSProveedorEJBService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public WSProveedorEJBService() {
        super(WSPROVEEDOREJBSERVICE_WSDL_LOCATION, new QName("http://proveedor.pge_service.pge_gis.edu/", "WSProveedorEJBService"));
    }

    /**
     * 
     * @return
     *     returns WSProveedor
     */
    @WebEndpoint(name = "WSProveedorPort")
    public WSProveedor getWSProveedorPort() {
        return (WSProveedor)super.getPort(new QName("http://proveedor.pge_service.pge_gis.edu/", "WSProveedorPort"), WSProveedor.class);
    }

}
