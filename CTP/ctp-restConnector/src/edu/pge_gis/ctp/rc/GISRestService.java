package edu.pge_gis.ctp.rc;

import java.util.Map;

import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.http.HttpRequest;
import org.jboss.soa.esb.message.Message;

import edu.pge_gis.ctp.rc.gis_ws_client.GisParams;

public class GISRestService implements ActionPipelineProcessor {

	/** OJOTA!!!!  todos los procesadores necesitan este constructor, pero hay que agregarlo a mano*/
	public GISRestService(ConfigTree config){
		
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
		// Este toma el request http y carga los parametros en el mensaje para usarlos a posteriori
		HttpRequest request = HttpRequest.getRequest(msg);
		GisParams params = parsearParametros(request);
		
		msg.getBody().add("params",params);
		
		String[] uri = request.getRequestURI().split("/");
		/**DEFINIMOS QUE los dos ultimos son el metodo y el servicio*/
		//servidor:puerto/ctp-restConnector/http/ctp/SERVICIO/METODO
		//localhost:8080/ctp-restConnector/http/ctp/meteorologia/getMap
		msg.getBody().add("method", uri[uri.length-1]);
		msg.getBody().add("serviceName", uri[uri.length-2]);
		return msg;
	}

	private GisParams parsearParametros(HttpRequest request) {
		GisParams params = new GisParams();
		System.out.println(request.getQueryParams());
		params.setService(request.getQueryParams().get("service")[0].toString());
		params.setLayers(request.getQueryParams().get("layers")[0].toString());
		params.setWidth(Integer.parseInt(request.getQueryParams().get("width")[0].toString()));
		params.setHeigth(Integer.parseInt(request.getQueryParams().get("height")[0].toString()));
		//etc....
		return params;
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
