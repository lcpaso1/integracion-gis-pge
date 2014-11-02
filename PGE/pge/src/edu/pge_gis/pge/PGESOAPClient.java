package edu.pge_gis.pge;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.io.IOUtils;
import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.http.HttpRequest;
import org.jboss.soa.esb.message.Message;

import edu.pge_gis.pge.utils.PGEConstants;

public class PGESOAPClient implements ActionPipelineProcessor {

	/** OJOTA!!!!  todos los procesadores necesitan este constructor, pero hay que agregarlo a mano*/
	public PGESOAPClient(ConfigTree config){
		
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
		// Este hace la invocacion del proveedor
		// obtenemos el request, o sea el request http del cliente
				HttpRequest request = HttpRequest.getRequest(msg);
				//por ahora ese request no sirve para nada, el body del reuqest http viene cargado en el body del mensaje esb
				String xml = msg.getBody().get(PGEConstants.KEY_xmlSoap).toString();
				System.out.println(request.getMethod());
				System.out.println(xml); //excelente funciona!!!
				//ahora hacemos invocacion http hacia el verdadero endpoint del webservice, o sea el proveedor.
				
				HttpClient httpClient = new HttpClient();
				/*traigo la configuracion */
				HashMap<String, String> serv = (HashMap<String, String>)msg.getBody().get(PGEConstants.KEY_servicio);
				PostMethod method = new PostMethod(serv.get(PGEConstants.KEY_urlProvider));
				int httpStatusCode;
				try {
					//antes de ejecutar, ponemos en el body el xml del soap
					StringRequestEntity sre = new StringRequestEntity(xml);
					method.setRequestEntity(sre);
					httpStatusCode = httpClient.executeMethod(method);
					assert (httpStatusCode == 200 || httpStatusCode == 204);
					//logHTTPStatusCode(httpStatusCode);
					InputStream response = method.getResponseBodyAsStream();
					System.out.println(" contenido mime:---"+method.getResponseHeaders());
					xml = new String(IOUtils.toByteArray(response),	method.getResponseCharSet());
					//ahora imprimo el resultado de la llamada al ws
					System.out.println("RESPUESTA PROVEEDOR: "+xml);//excelente funcionó de prima!!!
					//ahora ponemos el resultado en el msg para que el esb se lo mande al soapui
					msg.getBody().add(xml);
				} catch (HttpException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					armarMsgRespuesta(msg, "Error interno del servicio, ref: "+e.getMessage());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					armarMsgRespuesta(msg, "El servicio no esta disponible: "+e.getMessage());
				}
				//throw new ActionProcessingException("mensaje a llegar en soap");
		return msg;
	}

	private void armarMsgRespuesta(Message msg, String error) {
		// TODO Auto-generated method stub
		String xmlFault = "<env:Envelope xmlns:env='http://schemas.xmlsoap.org/soap/envelope/'><env:Header></env:Header><env:Body><env:Fault xmlns:env='http://schemas.xmlsoap.org/soap/envelope/'><faultcode>env:Server</faultcode><faultstring>"
				+ error +"</faultstring></env:Fault></env:Body></env:Envelope>";
		msg.getBody().add(xmlFault);
	}
	@Override
	public void processException(Message arg0, Throwable arg1) {
		// TODO Auto-generated method stub
		System.out.println("SOY pge soap client "+arg1.getClass().getCanonicalName());
	}

	@Override
	public void processSuccess(Message arg0) {
		// TODO Auto-generated method stub

	}

}
