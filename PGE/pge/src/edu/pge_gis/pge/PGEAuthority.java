package edu.pge_gis.pge;

import java.util.HashMap;

import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import edu.pge_gis.pge.utils.PGEConstants;
import edu.pge_gis.pge.utils.SQLUtils;

public class PGEAuthority implements ActionPipelineProcessor {

	/** OJOTA!!!!  todos los procesadores necesitan este constructor, pero hay que agregarlo a mano*/
	public PGEAuthority(ConfigTree config){
		
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
		// este define si el usuario tiene autorizacion para acceder a ese recurso
		HashMap<String, String> params = (HashMap<String, String>)msg.getBody().get(PGEConstants.KEY_params);
		HashMap<String, String> idmetodo = (HashMap<String, String>)msg.getBody().get(PGEConstants.KEY_metodo);
		boolean puede = SQLUtils.tienePermiso(params.get(PGEConstants.KEY_id_rol), idmetodo.get(PGEConstants.KEY_id));
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
