package edu.pge_gis.ctp.sc;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;

import edu.pge_gis.ctp.sc.exceptions.GISException;

@WebService(name="GISWS")
@HandlerChain(file="soap-handlers.xml")
public class GISWSwmsYwfs {
	// prop url servidor— p1
	// prop tipo servidor
	// prop agredados segun servidor:p2
	
	/*
	 * "localhost:8081" o  maps.meteorologia.gub.uy  o 152.12.32.55:8585
	 * "geoserver/integraciongispge/wms?"
	 *  geo o maps
	 *  http://p1/p2?params
	 * */
	private static String URLTEST = "http://localhost:8081/geoserver/integraciongispge/wms?";
	
	private byte[] invokeBinario(GISParams params){
		StringBuffer url = new StringBuffer(URLTEST);
		url.append(params.getParams());
		
		RESTInvoker invoker = new RESTInvoker();
		return invoker.invokeBinarioRestService(url.toString());
	}
	
	private String invokeText(GISParams params){
		StringBuffer url = new StringBuffer(URLTEST);
		url.append(params.getParams());
		
		RESTInvoker invoker = new RESTInvoker();
		return invoker.invokeRestService(url.toString());
	}
	
	//metodos del wms
	// response es un documento XML
	@WebMethod
	public String getCapabilities(GISParams params){
		return invokeText(params);
	}
	
	// response es una imagen
	@WebMethod
	public byte[] getMap(GISParams params){
		//service=WMS&version=1.1.0&request=GetMap&layers=integraciongispge:ine_ccz_mvd&styles=&bbox=551994.287963867,6133494.37103271,589199.424,6159798.69390869&width=512&height=361&srs=EPSG:32721&format=image%2Fjpeg
		//http://localhost:8080/ctp/http/ctp/meteorologia/getMap?service=WMS&version=1.1.0&request=GetMap&layers=integraciongispge:ine_ccz_mvd&styles=&bbox=551994.287963867,6133494.37103271,589199.424,6159798.69390869&width=512&height=361&srs=EPSG:32721&format=image%2Fjpeg
		//http://localhost:82/cgi-bin/mapserv.exe?map=mapfile.map&service=WMS&version=1.1.0&request=GetMap&layers=ine_ccz_mvd&styles=&bbox=551994.287963867,6133494.37103271,589199.424,6159798.69390869&width=512&height=361&srs=EPSG:32721&format=image%2Fjpeg
				
		return invokeBinario(params);
	}
	
	@WebMethod
	public String getFeatureInfo(GISParams params){
		return invokeText(params);
	}
	
	//metodos de wfs
	@WebMethod
	public String describeFeatureType(GISParams params){
		return invokeText(params);
	}
	
	@WebMethod
	public String getFeature(GISParams params){
		return invokeText(params);
	}
	
	@WebMethod
	public String getGmlObject(GISParams params){
		return invokeText(params);
	}
	
	@WebMethod
	public String transaction(GISParams params){
		return invokeText(params);
	}
	
	@WebMethod
	public String lockFeature(GISParams params){
		return invokeText(params);
	}
	
}
