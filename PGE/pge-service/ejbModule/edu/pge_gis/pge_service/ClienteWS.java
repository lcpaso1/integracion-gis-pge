package edu.pge_gis.pge_service;

import javax.xml.ws.BindingProvider;

import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import edu.pge_gis.pge_service.ws.client.WSProveedor;
import edu.pge_gis.pge_service.ws.client.WSProveedorEJBService;

public class ClienteWS implements ActionPipelineProcessor {

	public void destroy() throws ActionLifecycleException {
		// TODO Auto-generated method stub

	}

	public void initialise() throws ActionLifecycleException {
		// TODO Auto-generated method stub

	}

	/** OJOTA!!!!  todos los procesadores necesitan este constructor, pero hay que agregarlo a mano*/
	public ClienteWS(ConfigTree config){
		
	}
	
	public Message process(Message msg) throws ActionProcessingException {
		// TODO Auto-generated method stub
		System.out.println("***********************");
        System.out.println("Create Web Service Client...");
        WSProveedorEJBService service1 = new WSProveedorEJBService();
        System.out.println("Create Web Service...");
        WSProveedor port1 = service1.getWSProveedorPort();
        
        //** cambio el endpoint para que pase por el tunel, sino va directo al proveedor
        /* Set NEW Endpoint Location */
        String endpointURL = "http://localhost:8080/pge-service/http/soap/";
        BindingProvider bp = (BindingProvider)port1;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointURL);

        
        
        
        System.out.println("Call Web Service Operation...");
        System.out.println("Server said: " + port1.sumar(8,4));
        System.out.println("***********************");
        System.out.println("Call Over!");
		return msg;
	}

	public void processException(Message arg0, Throwable arg1) {
		// TODO Auto-generated method stub

	}

	public void processSuccess(Message arg0) {
		// TODO Auto-generated method stub

	}

}
