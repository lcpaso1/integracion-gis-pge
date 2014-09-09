package edu.pge_gis.pge;

import java.util.HashMap;

import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

public class PGECatalog implements ActionPipelineProcessor {

	/** OJOTA!!!!  todos los procesadores necesitan este constructor, pero hay que agregarlo a mano*/
	public PGECatalog(ConfigTree config){
		
	}
	@Override
	public void destroy() throws ActionLifecycleException {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialise() throws ActionLifecycleException {
		// TODO Auto-generated method stub

	}

	@Override
	public Message process(Message msg) throws ActionProcessingException {
		// Este define el proveedor y agrega o quita cabezales ws-addressing del xml soap
		//en base a los cabezales addressing, sacamos la url del proveedor
		String xml = msg.getBody().get("xmlSoap").toString();
		System.out.println(xml);
		HashMap<String, String> params = (HashMap<String, String>)msg.getBody().get("params");
		//busco el servicio por la dir logica
		HashMap<String, String> serv = SQLUtils.getServicio(params.get("dir_logica"));
		System.out.println(serv.get("urlProvider"));
		//busco el metodo
		HashMap<String, String> metodo = SQLUtils.getMetodo(serv.get("id"), params.get("metodo"));
		
		//obtener metodo
		//por ahora solo agrego una
		msg.getBody().add("servicio", serv);
		msg.getBody().add("metodo", metodo);
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
