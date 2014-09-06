package edu.pge_gis.ctp.sc;

import edu.pge_gis.ctp.sc.exceptions.GISException;
import edu.pge_gis.utils.ClienteHttp;

public class RESTInvoker {

	public byte[] invokeBinarioRestService(String url){
		return ClienteHttp.executeGetBinario(url);
	}
	
	public String invokeRestService(String url){
		return ClienteHttp.executeGet(url);
	}
}
