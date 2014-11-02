package edu.pge_gis.ctp.rc;

import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import uy.gub.agesic.beans.SAMLAssertion;


public class SecurityAggregator implements ActionPipelineProcessor {

	/** OJOTA!!!!  todos los procesadores necesitan este constructor, pero hay que agregarlo a mano*/
	public SecurityAggregator(ConfigTree config){
		
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
		// Este toma los parametros para ver que usuario usa para pedir el token de seguridad

		// TODO: obtener el token de seguridad del STS 
		String securityToken = "&&&&&&&&&&&&&&&&_dummy_security_token_&&&&&&&&&&&&&&&&&&&";
		
		// Dummy SAMLAssertion 

		SAMLAssertion sas = new SAMLAssertion();
		sas.setAssertion(null);
	
		// agrego el token de seguridad al mensaje
		msg.getBody().add("security_token", sas);
		
		
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
