package edu.pge_gis.pge;

import java.util.HashMap;

import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import edu.pge_gis.pge.utils.PGEConstants;
import edu.pge_gis.pge.utils.SQLUtils;

public class PGEAuthenticate implements ActionPipelineProcessor {

	/** OJOTA!!!!  todos los procesadores necesitan este constructor, pero hay que agregarlo a mano*/
	public PGEAuthenticate(ConfigTree config){
		
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
		// este verifica con el sts el token del cabezal saml del soap y obtiene los permisos del usuario
		HashMap<String, String> params = (HashMap<String, String>)msg.getBody().get(PGEConstants.KEY_params);
		long rol = SQLUtils.getIdRol(params.get(PGEConstants.KEY_rol));
		params.put(PGEConstants.KEY_id_rol, ""+rol);
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
