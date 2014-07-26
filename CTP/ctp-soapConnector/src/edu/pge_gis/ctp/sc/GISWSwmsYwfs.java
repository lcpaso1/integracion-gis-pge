package edu.pge_gis.ctp.sc;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(name="GISWS")
public class GISWSwmsYwfs {
	//todos los metodos en este ws, usan el soap conector para ir hacia el geo server.
	
	//metodos del wms
	// response es un documento XML
	@WebMethod
	public String getCapabilities(GISParams params){
		return null;
	}
	
	// response es una imagen
	@WebMethod
	public String getMap(GISParams params){
		return null;
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
