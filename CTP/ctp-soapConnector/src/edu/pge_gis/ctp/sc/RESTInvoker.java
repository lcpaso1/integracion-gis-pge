package edu.pge_gis.ctp.sc;

import java.io.IOException;

import edu.pge_gis.utils.ClienteHttp;

public class RESTInvoker {

	public byte[] invokeBinarioRestService(String url){
		try {
			return ClienteHttp.executeGetBinario(url);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public String invokeRestService(String url){
		try {
			return ClienteHttp.executeGet(url);
		} catch (IOException e) {
			// Lanzo dentro de runtime para que el ws funcione, sin pedir declarar la excepcion
			throw new RuntimeException(e);
		}
	}
	
	public String invokeRestServiceByPost(String url, String postParams){
		try {
			postParams = postParams.replaceAll("xsi:schemaLocation=\".*?\"", "");
			return ClienteHttp.executePost(url, postParams);
		} catch (IOException e) {
			// Lanzo dentro de runtime para que el ws funcione, sin pedir declarar la excepcion
			throw new RuntimeException(e);
		}
	}
}
