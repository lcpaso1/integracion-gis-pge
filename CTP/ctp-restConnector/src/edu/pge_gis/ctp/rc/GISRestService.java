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
		System.out.println(request.getQueryParams());
		if (request.getQueryParams().get("request")[0].toString().equalsIgnoreCase("GetMap"))
			return getMap(request);
		else if (request.getQueryParams().get("request")[0].toString().equalsIgnoreCase("GetCapabilities"))
			return getCapabilities(request);
		else if (request.getQueryParams().get("request")[0].toString().equalsIgnoreCase("GetFeatureInfo"))
			return getFeatureInfo(request);
		
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
	
	private GisParams getMap(HttpRequest request){
		GisParams params = new GisParams();
		
		//obligatorios: service, version, request, layers, styles, srs, bbox, width, heigth, format
		params.setService(request.getQueryParams().get("service")[0].toString());
		params.setVersion(request.getQueryParams().get("version")[0].toString());
		params.setRequest("GetMap");
		params.setLayers(request.getQueryParams().get("layers")[0].toString());
		params.setStyles(request.getQueryParams().get("styles")[0].toString());
		params.setSrs(request.getQueryParams().get("srs")[0].toString());
		params.setBbox(request.getQueryParams().get("bbox")[0].toString());
		params.setWidth(Integer.parseInt(request.getQueryParams().get("width")[0].toString()));
		params.setHeigth(Integer.parseInt(request.getQueryParams().get("height")[0].toString()));
		params.setFormat(request.getQueryParams().get("format")[0].toString());
		
		//opcionales: transparent, bgcolor, exceptions, time, elevation, otherSampleDimensions
		if (request.getQueryParams().get("transparent") != null){
			params.setTransparent(request.getQueryParams().get("transparent")[0].toString());
		}
		if (request.getQueryParams().get("bgcolor") != null){
			params.setBgcolor(request.getQueryParams().get("bgcolor")[0].toString());
		}
		if (request.getQueryParams().get("exceptions") != null){
			params.setExceptions(request.getQueryParams().get("exceptions")[0].toString());
		}
		if (request.getQueryParams().get("time") != null){
			params.setTime(request.getQueryParams().get("time")[0].toString());
		}
		if (request.getQueryParams().get("elevation") != null){
			params.setElevation(request.getQueryParams().get("elevation")[0].toString());
		}
		
		return params;
	}
	
	private GisParams getCapabilities(HttpRequest request){
		GisParams params = new GisParams();
		
		// obligatorios: service, request
		params.setService(request.getQueryParams().get("service")[0].toString());
		params.setRequest("GetCapabilities");
				
		// opcionales: version, format, updateSequence
		if (request.getQueryParams().get("version") != null){
			params.setVersion(request.getQueryParams().get("version")[0].toString());
		}
		if (request.getQueryParams().get("format") != null){
			params.setFormat(request.getQueryParams().get("format")[0].toString());
		}
		if (request.getQueryParams().get("updatesequence") != null){
			params.setUpdateSequence(request.getQueryParams().get("updatesequence")[0].toString());
		}
		
		return params;
	}
	
	private GisParams getFeatureInfo(HttpRequest request){
		GisParams params = new GisParams();
		
		// obligatorios: version, request, mapRequestPart, queryLayers, infoFormat, i, j
		params.setVersion(request.getQueryParams().get("version")[0].toString());
		params.setRequest("GetFeatureInfo");
		params.setMapRequestPart(request.getQueryParams().get("maprequestpart")[0].toString());
		params.setQueryLayers(request.getQueryParams().get("querylayers")[0].toString());
		params.setInfoFormat(request.getQueryParams().get("infoformat")[0].toString());
		params.setI(Integer.parseInt(request.getQueryParams().get("i")[0].toString()));
		params.setJ(Integer.parseInt(request.getQueryParams().get("j")[0].toString()));
		
		// opcionales: featureCount, exceptions
		if (request.getQueryParams().get("featurecount") != null){
			params.setFeatureCount(Integer.parseInt(request.getQueryParams().get("version")[0].toString()));
		}
		if (request.getQueryParams().get("exceptions") != null){
			params.setExceptions(request.getQueryParams().get("exceptions")[0].toString());
		}
		
		return params;
	}

}
