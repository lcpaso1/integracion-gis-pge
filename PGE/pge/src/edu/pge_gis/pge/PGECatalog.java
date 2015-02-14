package edu.pge_gis.pge;

import java.util.HashMap;

import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import edu.pge_gis.pge.utils.PGEConstants;
import edu.pge_gis.pge.utils.SQLUtils;

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
		String xml = msg.getBody().get(PGEConstants.KEY_xmlSoap).toString();
		System.out.println(xml);
		HashMap<String, String> params = (HashMap<String, String>)msg.getBody().get(PGEConstants.KEY_params);
		//busco el servicio por la dir logica
		HashMap<String, String> serv = SQLUtils.getServicio(params.get(PGEConstants.KEY_dir_logica));
		if(serv == null){
			throw new ActionProcessingException("No existe el servicio que trata de invocar.");
		}
		System.out.println(serv.get(PGEConstants.KEY_urlProvider));
		//busco el metodo
		HashMap<String, String> metodo = SQLUtils.getMetodo(serv.get(PGEConstants.KEY_id), params.get(PGEConstants.KEY_metodo));
		if(metodo == null){
			throw new ActionProcessingException("El servicio no tiene el metodo indicado.");
		}
		//obtener metodo
		//por ahora solo agrego una
		msg.getBody().add(PGEConstants.KEY_servicio, serv);
		msg.getBody().add(PGEConstants.KEY_metodo, metodo);
		return msg;
	}

	@Override
	public void processException(Message arg0, Throwable arg1) {
		// TODO Auto-generated method stub
		System.out.println("SOY pge catalog "+arg1.getClass().getCanonicalName());
	}

	@Override
	public void processSuccess(Message arg0) {
		// TODO Auto-generated method stub

	}

}
