package edu.pge_gis.pge_service;

import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

public class PGESecurity implements ActionPipelineProcessor {

	public PGESecurity(ConfigTree config){
		
	}
	
	public void destroy() throws ActionLifecycleException {
		// TODO Auto-generated method stub
		
	}

	public void initialise() throws ActionLifecycleException {
		// TODO Auto-generated method stub
		
	}

	public Message process(Message arg0) throws ActionProcessingException {
		// TODO Auto-generated method stub
		return arg0;
	}

	public void processException(Message arg0, Throwable arg1) {
		// TODO Auto-generated method stub
		
	}

	public void processSuccess(Message arg0) {
		// TODO Auto-generated method stub
		
	}

}
