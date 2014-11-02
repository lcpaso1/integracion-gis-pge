package edu.pge_gis.ctp.rc;

import static edu.pge_gis.ctp.dto.InfoServicio.DIRECCION_LOGICA;
import static edu.pge_gis.ctp.dto.InfoServicio.DIRECCION_PROXY;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.addressing.AddressingBuilder;
import javax.xml.ws.addressing.AttributedURI;
import javax.xml.ws.addressing.JAXWSAConstants;
import javax.xml.ws.addressing.soap.SOAPAddressingBuilder;
import javax.xml.ws.addressing.soap.SOAPAddressingProperties;
import javax.xml.ws.handler.Handler;

import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.jboss.ws.extensions.addressing.AttributedURIImpl;
import org.jboss.ws.extensions.addressing.jaxws.WSAddressingClientHandler;

import uy.gub.agesic.beans.SAMLAssertion;
import edu.pge_gis.ctp.dto.InfoServicio;
import edu.pge_gis.ctp.rc.gis_ws_client.GISWS;
import edu.pge_gis.ctp.rc.gis_ws_client.GISWSwmsYwfsService;
import edu.pge_gis.ctp.rc.gis_ws_client.GisParams;

public class CTPSOAPClient implements ActionPipelineProcessor {

	/** OJOTA!!!!  todos los procesadores necesitan este constructor, pero hay que agregarlo a mano*/
	public CTPSOAPClient(ConfigTree config){
		
	}
	@Override
	public void destroy() throws ActionLifecycleException {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialise() throws ActionLifecycleException {
		// TODO Auto-generated method stub

	}

	private final static String pge_endpointURL = "http://localhost:8080/pge/http/pge/";
	// la forma de esta url es: [ip y puerto]/[nombre del .esb]/[protocolo del listener]/[uri del servicio]/
	
	@Override
	public Message process(Message msg) throws ActionProcessingException {
		// Este arma el pedido soap e invoca al ws, pasando por la pge.
		GisParams params = armarParametros(msg);
		GISWSwmsYwfsService ws = new GISWSwmsYwfsService();
		GISWS port = ws.getGISWSPort();
		
		InfoServicio datosServicio = (InfoServicio)msg.getBody().get(InfoServicio.INFO_SERVICIO);
		String metodo = datosServicio.datos.get(InfoServicio.NOMBRE_METODO);
		String metodoURI =datosServicio.datos.get(InfoServicio.NOMBRE_METODO_XML);

		SAMLAssertion securityToken = (SAMLAssertion)msg.getBody().get("security_token");
		port = agregarCabezalesAddressing(datosServicio.datos.get(DIRECCION_LOGICA), metodoURI, port, securityToken);
		//este siempre va a la pge, pasar url pge a property
		BindingProvider bp = (BindingProvider)port;
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, datosServicio.datos.get(DIRECCION_PROXY));
        
        //solo hay diferencia en el getMap, que es binario, el resto es string.
        try {
        	//magia de reflection, una vez todo configurado, en base al nombre invoco el metodo, sin tener que hacer
        	//un if por cada metodo.
        	//TODO ver que de esta forma igual invoka los handlers para agregar los cabezales.
			Method metodoWS = port.getClass().getDeclaredMethod(metodo, GisParams.class);
			msg.getBody().add(metodoWS.invoke(port, params));
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}
	
	private GisParams armarParametros(Message msg) {
		
		return (GisParams)msg.getBody().get("params");
	}
	private GISWS agregarCabezalesAddressing(String serviceName, String method, GISWS port, SAMLAssertion securityToken){
		List<Handler> customHandlerChain =	new	ArrayList<Handler>();
		customHandlerChain.add(new	WSAddressingClientHandler());
		//Build addressing properties
		AddressingBuilder addrBuilder =	SOAPAddressingBuilder.getAddressingBuilder();
		SOAPAddressingProperties addrProps =(SOAPAddressingProperties)addrBuilder.newAddressingProperties();
		AttributedURI to =	new	AttributedURIImpl(serviceName);
		AttributedURI action =	new	AttributedURIImpl(method);
		addrProps.setTo(to);
		addrProps.setAction(action);
		
		BindingProvider bindingProvider = (BindingProvider)port;
		bindingProvider.getRequestContext().put(JAXWSAConstants.CLIENT_ADDRESSING_PROPERTIES, addrProps);
		bindingProvider.getBinding().setHandlerChain(customHandlerChain);
		
		// TODO : agregar securityToken a SOAP message.
		bindingProvider.getRequestContext().put("uy.gub.agesic.security.saml", securityToken); 

		
		return port;
	}

	@Override
	public void processException(Message arg0, Throwable arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processSuccess(Message arg0) {
		// TODO Auto-generated method stub

	}

}
