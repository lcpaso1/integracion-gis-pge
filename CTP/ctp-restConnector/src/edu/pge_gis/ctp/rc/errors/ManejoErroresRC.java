package edu.pge_gis.ctp.rc.errors;

import javax.xml.ws.soap.SOAPFaultException;

import org.jboss.soa.esb.Service;
import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.client.ServiceInvoker;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.http.HttpResponse;
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
		// este metodo es solo un pasa mano, aca no hay que hacer nada!!!!
		return arg0;
	}

	/**
	 * este metodo es invocado por el servicio de manejo de errores,
	 * transforma el mensaje de excepcion en una respuesta acorde al tipo de error
	 * @param msg
	 * @return
	 * @throws ActionProcessingException
	 */
	public Message processErrors(Message msg) throws ActionProcessingException {
		System.out.println("soy processErrors me inocaron yupi!");
		// segun la excepcion hago algo diferente si es una soapfault obtengo el contenido y lo mando
		//sino mando el mensaje
		Throwable t = (Throwable)msg.getBody().get("exceptionTrace");
		//por defecto tomo el error como viene
		String errorMsg = t.getMessage();
		//en algunos casos el mensaje hay que modificarlo
		if(t instanceof SOAPFaultException){
			//hay otra que es de rpc
			
		}
		//seteo el mensaje en el boydy del msg esb
		msg.getBody().add(errorMsg);
		//obtengo el status code de la excepcion
		int responseCode = 500;
		// me protejo en caso que sea otra excepcion
		if(t instanceof HTTPStatusCode)
			responseCode = ((HTTPStatusCode)t).statusCode();
		//creo la respuesta y seteo el mensaje
		HttpResponse resp = new HttpResponse(responseCode);
		resp.setResponse(msg);
		return msg;
	}
	
	/**
	 * este metodo invoca el servicio de manejo de errores para que retorna de la forma que nosotros queremos.
	 */
	@Override
	public void processException(Message msg, Throwable t) {
		System.out.println("soy manejoErrores procesando excpecion");
		if( msg != null ) {
			msg.getBody().add("exceptionTrace",t);
			try {
				Service errorService = new Service("Utility", "ServeErrorService");
				ServiceInvoker si = new ServiceInvoker(errorService);
				msg = si.deliverSync(msg, 1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void processSuccess(Message arg0) {
		// TODO Auto-generated method stub

	}

}
