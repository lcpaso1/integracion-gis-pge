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
		//lanzarExceptionDePrueba();
		//http://localhost:82/cgi-bin/mapserv.exe?map=padronesmvd.map&SERVICE=WMS&VERSION=1.1.1&REQUEST=GetCapabilities
		//http://localhost:8080/ctp/http/ctp/catastro?service=WMS&version=1.1.0&request=getcapabilities
		//http://localhost:8080/ctp/http/ctp/catastro?service=WFS&version=1.1.0&request=getcapabilities
		boolean isWms = params.getService().equalsIgnoreCase("wms");
		String result = invokeText(params, isWms);
		
		String replacement = params.getUrlReplace();
		PropertiesHandler prop = PropertiesHandler.getInstance();
		StringBuffer url = new StringBuffer(prop.getProperty("url"));
		String propows = prop.getProperty("propows");
		
		// primero se remplaza la opcion mas larga y luego la mas corta 
		if (isWms){
			result = result.replaceAll(url+prop.getProperty("propwms"), replacement);
			result = (propows != null) && !propows.equals("") ? result.replaceAll(url+propows, replacement) : result;
			result = result.replaceAll(url.toString(), replacement);
			
		}else{
			result = result.replaceAll(url+prop.getProperty("propwfs"), replacement);
			result = (propows != null) && !propows.equals("") ? result.replaceAll(url+propows, replacement) : result;
			result = result.replaceAll(url.toString(), replacement);
		}
		
		return result;
	}
	
	private void lanzarExceptionDePrueba(){
		try {
			Thread.sleep(35000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new RuntimeException("prueba error desde ctp sc");
	}
	
	// response es una imagen
	@WebMethod
	public byte[] getMap(GISParams params){
		//lanzarExceptionDePrueba();
		//http://localhost:82/cgi-bin/mapserv.exe?map=padronesmvd.map&service=WMS&version=1.1.1&request=GetMap&layers=padrones-montevideo&styles=&bbox=551994.287963867,6133494.37103271,589199.424,6159798.69390869&width=512&height=361&srs=EPSG:32721&format=image%2Fjpeg
		//http://localhost:8080/ctp/http/ctp/catastro?service=WMS&version=1.1.0&request=GetMap&layers=padrones-montevideo&styles=&bbox=553867.5625,6134514.0,588288.8125,6153362.5&width=602&height=330&srs=EPSG:32721&format=image%2Fjpeg
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
