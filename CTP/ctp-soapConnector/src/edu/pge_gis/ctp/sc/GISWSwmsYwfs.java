package edu.pge_gis.ctp.sc;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(name="GISWS")
@HandlerChain(file="soap-handlers.xml")
public class GISWSwmsYwfs {
	
	private static String URLTEST = "http://localhost:8081/geoserver/integraciongispge/wms?";
	
	//metodos del wms
	// response es un documento XML
	@WebMethod
	public String getCapabilities(GISParams params){
		return null;
	}
	
	// response es una imagen
	@WebMethod
	public byte[] getMap(GISParams params){
		//service=WMS&version=1.1.0&request=GetMap&layers=integraciongispge:ine_ccz_mvd&styles=&bbox=551994.287963867,6133494.37103271,589199.424,6159798.69390869&width=512&height=361&srs=EPSG:32721&format=image%2Fjpeg
		http://localhost:8080/ctp/http/ctp/meteorologia/getMap?service=WMS&version=1.1.0&request=GetMap&layers=integraciongispge:ine_ccz_mvd&styles=&bbox=551994.287963867,6133494.37103271,589199.424,6159798.69390869&width=512&height=361&srs=EPSG:32721&format=image%2Fjpeg
		System.out.println(params.getLayers());
		
		StringBuffer url = new StringBuffer(URLTEST);
		
		//obligatorios: version, request, layers, styles, crs, bbox, width, heigth, format
		url.append("service="); url.append(params.getService()); url.append("&");
		url.append("version="); url.append(params.getVersion()); url.append("&");
		url.append("request="); url.append(params.getRequest()); url.append("&");
		url.append("layers="); url.append(params.getLayers()); url.append("&");
		url.append("styles="); url.append(params.getStyles()); url.append("&");
		url.append("bbox="); url.append(params.getBbox()); url.append("&");
		url.append("width="); url.append(params.getWidth()); url.append("&");
		url.append("height="); url.append(params.getHeigth()); url.append("&");
		url.append("format="); url.append(params.getFormat()); url.append("&");
		url.append("srs="); url.append(params.getSrs()); 
		
		//opcionales: transparent, bgcolor, exceptions, time, elevation, otherSampleDimensions
		if ((params.getTransparent() != null) && !params.getTransparent().equals(""))
			url.append("&transparent="); url.append(params.getTransparent());
			
		if ((params.getBgcolor() != null) && !params.getBgcolor().equals(""))
			url.append("&bgcolor="); url.append(params.getBgcolor());
			
		if ((params.getExceptions() != null) && !params.getExceptions().equals(""))
			url.append("&exceptions="); url.append(params.getExceptions());
			
		if ((params.getTime() != null) && !params.getTime().equals(""))
			url.append("&time="); url.append(params.getTime());
	
		if ((params.getElevation() != null) && !params.getElevation().equals(""))
			url.append("&elevation="); url.append(params.getElevation());
		
		
		RESTInvoker i = new RESTInvoker();
		
		return i.invokeBinarioRestService(url.toString());
	}
	
	// response es de acuerdo al formato indicado en el parametro infoFormat
	@WebMethod
	public String getFeatureInfo(GISParams params){
		return null;
	}
	
	
	//metodos de wfs
	@WebMethod
	public String describeFeatureType(GISParams params){
		return null;
	}
	
	@WebMethod
	public String getFeature(GISParams params){
		return null;
	}
	
	@WebMethod
	public String getGmlObject(GISParams params){
		return null;
	}
	
	@WebMethod
	public String transaction(GISParams params){
		return null;
	}
	
	@WebMethod
	public String lockFeature(GISParams params){
		return null;
	}
	
}
