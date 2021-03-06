package edu.pge_gis.pge_service.ws;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.io.IOUtils;
import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.http.HttpRequest;
import org.jboss.soa.esb.message.Message;

public class SoapHTTPClient implements ActionPipelineProcessor {
	/* esta clase sencilla, toma del body del mensaje http el xml del soap:envelope y ejecuta un get http hacia un endpoint
	 * de ws pasando ese xml en el body del mensaje.
	 * 
	 * Luego toma del body del http response el xml del soap de respuesta del proveedor y lo pone en el body del mensaje
	 * para que el esb lo retorne al cliente.
	 * 
	 * Esto simula el ultimo paso de la pge, el que rutea el mensaje soap hacia el proveedor.
	 * 
	 * */
	
	/** OJOTA!!!!  todos los procesadores necesitan este constructor, pero hay que agregarlo a mano*/
	public SoapHTTPClient(ConfigTree config){
		
	}
	
	public Message process(Message msg) throws ActionProcessingException {
		// obtenemos el request, o sea el request http del cliente
		HttpRequest request = HttpRequest.getRequest(msg);
		//por ahora ese request no sirve para nada, el body del reuqest http viene cargado en el body del mensaje esb
		String xml = new String(msg.getBody().get().toString());
		System.out.println(request.getMethod());
		System.out.println(xml); //excelente funciona!!!
		//ahora hacemos invocacion http hacia el verdadero endpoint del webservice, o sea el proveedor.
		
		HttpClient httpClient = new HttpClient();
		PostMethod method = new PostMethod("http://localhost:8080/proveedorSOAP/WSProveedor");
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
			System.out.println(xml);//excelente funcion� de prima!!!
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
	
	public void destroy() throws ActionLifecycleException {
		// TODO Auto-generated method stub

	}

	public void initialise() throws ActionLifecycleException {
		// TODO Auto-generated method stub

	}

	

	public void processException(Message arg0, Throwable arg1) {
		// TODO Auto-generated method stub

	}

	public void processSuccess(Message arg0) {
		// TODO Auto-generated method stub

	}

}
