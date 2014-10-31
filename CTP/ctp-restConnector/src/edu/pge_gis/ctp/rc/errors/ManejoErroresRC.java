package edu.pge_gis.ctp.rc.errors;

import javax.xml.ws.soap.SOAPFaultException;

import org.jboss.soa.esb.Service;
import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

public class ManejoErroresRC implements ActionPipelineProcessor {

	/** OJOTA!!!!  todos los procesadores necesitan este constructor, pero hay que agregarlo a mano*/
	public ManejoErroresRC(ConfigTree config){
		
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
	public Message process(Message arg0) throws ActionProcessingException {
		// TODO Auto-generated method stub
		return arg0;
	}

	public Message processErrors(Message msg) throws ActionProcessingException {
		System.out.println("soy processErrors me inocaron yupi!");
		// segun la excepcion hago algo diferente si es una soapfault obtengo el contenido y lo mando
		//sino mando el mensaje
		Throwable t = (Throwable)msg.getBody().get("exceptionTrace");
		if(t instanceof SOAPFaultException){
			//hay otra que es de rpc
		}
		else
			msg.getBody().add(t.getMessage());
		return msg;
	}
	
	
	@Override
	public void processException(Message msg, Throwable t) {
		System.out.println("soy manejoErrores procesando excpecion");
		if( msg != null ){
			msg.getBody().add("exceptionTrace",t);
			try {
				Service errorService = new Service("Utility", "ServeErrorService");
				ServiceInvoker si = new ServiceInvoker(errorService);
				msg = si.deliverSync(msg, 1000);
			} catch (Exception e) {
				e.printStackTrace();
				//throw new ActionProcessingException(e.getMessage(), e);
			}
		}

	}

	@Override
	public void processSuccess(Message arg0) {
		// TODO Auto-generated method stub

	}

}
