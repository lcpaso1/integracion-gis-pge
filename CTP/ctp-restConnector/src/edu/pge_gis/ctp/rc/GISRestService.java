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
		// obtener ip, sevicio, metodo
		// to=dir logica, action=metodo a partir de lo anterior 
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
		System.out.println(request.getQueryParams());
		if (request.getQueryParams().get("request")[0].toString().equalsIgnoreCase("GetMap"))
			return getMap(request);
		else if (request.getQueryParams().get("request")[0].toString().equalsIgnoreCase("GetCapabilities"))
			return getCapabilities(request);
		else if (request.getQueryParams().get("request")[0].toString().equalsIgnoreCase("GetFeatureInfo"))
			return getFeatureInfo(request);
		else if (request.getQueryParams().get("request")[0].toString().equalsIgnoreCase("DescribeFeatureType"))
			return describeFeatureType(request);
		else if (request.getQueryParams().get("request")[0].toString().equalsIgnoreCase("GetFeature"))
			return getFeature(request);
		else if (request.getQueryParams().get("request")[0].toString().equalsIgnoreCase("GetGmlObject"))
			return getGmlObject(request);
		else if (request.getQueryParams().get("request")[0].toString().equalsIgnoreCase("Transaction"))
			return transaction(request);
		else if (request.getQueryParams().get("request")[0].toString().equalsIgnoreCase("LockFeature"))
			return lockFeature(request);
		else
			return null;
	}
	@Override
	public void processException(Message arg0, Throwable arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processSuccess(Message arg0) {
		// TODO Auto-generated method stub

	}
	
	private GisParams createParams(String service, String method, String parameters){
		GisParams params = new GisParams();
		params.setRequest(method);
		params.setService(service);
		params.setParams(parameters);
		
		return params;
	}
	
	private GisParams getMap(HttpRequest request){
		return createParams(request.getQueryParams().get("service")[0].toString(), 
				"GetMap", 
				request.getQueryString());
	}
	
	private GisParams getCapabilities(HttpRequest request){
		return createParams(request.getQueryParams().get("service")[0].toString(), 
				"GetCapabilities", 
				request.getQueryString());
	}
	
	private GisParams getFeatureInfo(HttpRequest request){
		return createParams(request.getQueryParams().get("service")[0].toString(), 
				"GetFeatureInfo", 
				request.getQueryString());
	}
	
	private GisParams describeFeatureType(HttpRequest request){
		return createParams(request.getQueryParams().get("service")[0].toString(), 
				"DescribeFeatureType", 
				request.getQueryString());
	}

	private GisParams getFeature(HttpRequest request){
		return createParams(request.getQueryParams().get("service")[0].toString(), 
				"GetFeature", 
				request.getQueryString());
	}
	
	private GisParams getGmlObject(HttpRequest request){
		return createParams(request.getQueryParams().get("service")[0].toString(), 
				"GetGmlObject", 
				request.getQueryString());
	}
	
	private GisParams transaction(HttpRequest request){
		return createParams(request.getQueryParams().get("service")[0].toString(), 
				"Transaction", 
				request.getQueryString());
	}
	
	private GisParams lockFeature(HttpRequest request){
		return createParams(request.getQueryParams().get("service")[0].toString(), 
				"LockFeature", 
				request.getQueryString());
	}
}
