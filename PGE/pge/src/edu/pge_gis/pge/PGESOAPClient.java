package edu.pge_gis.pge;

import java.io.IOException;
import java.io.InputStream;

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
				String xml = msg.getBody().get("xmlSoap").toString();
				System.out.println(request.getMethod());
				System.out.println(xml); //excelente funciona!!!
				//ahora hacemos invocacion http hacia el verdadero endpoint del webservice, o sea el proveedor.
				
				HttpClient httpClient = new HttpClient();
				/* TODO esta url la debe tomar desde lo que dice el PGEcatalog */
				PostMethod method = new PostMethod(msg.getBody().get("urlProvider").toString());
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
					System.out.println(xml);//excelente funcionó de prima!!!
					//ahora ponemos el resultado en el msg para que el esb se lo mande al soapui
					msg.getBody().add(xml);
				} catch (HttpException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
