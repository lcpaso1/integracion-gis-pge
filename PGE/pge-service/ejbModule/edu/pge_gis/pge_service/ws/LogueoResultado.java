package edu.pge_gis.pge_service.ws;

import java.util.HashMap;

import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

public class LogueoResultado implements ActionPipelineProcessor {

	public LogueoResultado(ConfigTree config){
		
	}
	
	public void destroy() throws ActionLifecycleException {
		// TODO Auto-generated method stub

	}

	public void initialise() throws ActionLifecycleException {
		// TODO Auto-generated method stub

	}

	public Message process(Message msg) throws ActionProcessingException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params = (HashMap<String, Object>) msg.getBody().get("response");
		
		//msg.getBody().add(params.get("getFeatureInfoResponse.return"));
		System.out.println(params);
		return msg;
	}

	public void processException(Message arg0, Throwable arg1) {
		// TODO Auto-generated method stub

	}

	public void processSuccess(Message arg0) {
		// TODO Auto-generated method stub

	}

}
