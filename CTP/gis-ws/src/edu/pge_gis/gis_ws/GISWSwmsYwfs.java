package edu.pge_gis.gis_ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(name="GISWS")
public class GISWSwmsYwfs {

	//metodos del wms
	
	@WebMethod
	public String getMap(GISParams params){
		//todos los metodos en este ws, usan el soap conector para ir hacia el geo server.
		return null;
	}
	//etc...
	
	
	//metodos de wfs
	
}
