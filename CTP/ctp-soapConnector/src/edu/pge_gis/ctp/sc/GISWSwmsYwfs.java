package edu.pge_gis.ctp.sc;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;

import edu.pge_gis.ctp.sc.handlers.PropertiesHandler;

@WebService(name="GISWS")
@HandlerChain(file="soap-handlers.xml")
public class GISWSwmsYwfs {

	private byte[] invokeBinario(GISParams params, boolean isWms){
		PropertiesHandler prop = PropertiesHandler.getInstance();
		StringBuffer url = new StringBuffer(prop.getProperty("url"));
		
		if (isWms)
			url.append(prop.getProperty("propwms"));
		else
			url.append(prop.getProperty("propwfs"));
		
		url.append(params.getParams());
		
		RESTInvoker invoker = new RESTInvoker();
		return invoker.invokeBinarioRestService(url.toString());
	}
	
	private String invokeText(GISParams params, boolean isWms){
		PropertiesHandler prop = PropertiesHandler.getInstance();
		StringBuffer url = new StringBuffer(prop.getProperty("url"));
		
		if (isWms)
			url.append(prop.getProperty("propwms"));
		else
			url.append(prop.getProperty("propwfs"));
		
		url.append(params.getParams());
		
		RESTInvoker invoker = new RESTInvoker();
		return invoker.invokeRestService(url.toString());
	}
	
	//metodos del wms
	// response es un documento XML
	@WebMethod
	public String getCapabilities(GISParams params){
		boolean isWms = params.getService().equalsIgnoreCase("wms");
		return invokeText(params, isWms);
	}
	
	// response es una imagen
	@WebMethod
	public byte[] getMap(GISParams params){
		//service=WMS&version=1.1.0&request=GetMap&layers=integraciongispge:ine_ccz_mvd&styles=&bbox=551994.287963867,6133494.37103271,589199.424,6159798.69390869&width=512&height=361&srs=EPSG:32721&format=image%2Fjpeg
		//http://localhost:8080/ctp/http/ctp/meteorologia/getMap?service=WMS&version=1.1.0&request=GetMap&layers=integraciongispge:ine_ccz_mvd&styles=&bbox=551994.287963867,6133494.37103271,589199.424,6159798.69390869&width=512&height=361&srs=EPSG:32721&format=image%2Fjpeg
		//http://localhost:82/cgi-bin/mapserv.exe?map=mapfile.map&service=WMS&version=1.1.0&request=GetMap&layers=ine_ccz_mvd&styles=&bbox=551994.287963867,6133494.37103271,589199.424,6159798.69390869&width=512&height=361&srs=EPSG:32721&format=image%2Fjpeg
		return invokeBinario(params, true);
	}
	
	@WebMethod
	public String getFeatureInfo(GISParams params){
		return invokeText(params, true);
	}
	
	//metodos de wfs
	@WebMethod
	public String describeFeatureType(GISParams params){
		return invokeText(params, false);
	}
	
	@WebMethod
	public String getFeature(GISParams params){
		return invokeText(params, false);
	}
	
	@WebMethod
	public String getGmlObject(GISParams params){
		return invokeText(params, false);
	}
	
	@WebMethod
	public String transaction(GISParams params){
		return invokeText(params, false);
	}
	
	@WebMethod
	public String lockFeature(GISParams params){
		return invokeText(params, false);
	}
	
}
