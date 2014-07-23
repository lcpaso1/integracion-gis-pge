package edu.pge_gis.ctp.rc;

import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

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
		//TODO crear el ws y generar el cliente, en el paquete, luego invocarlo.
		return msg;
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
