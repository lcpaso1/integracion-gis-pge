package edu.pge_gis.ctp.rc.errors;

public class CTPServiceException extends RuntimeException implements HTTPStatusCode {

	int sc = 404;
	
	@Override
	public int statusCode() {
		// TODO Auto-generated method stub
		return sc;
	}
	//exception que será lanzada dentro de este ctp, si hay algun problema de configuración o configuracion con la pge.
	public CTPServiceException(String msg){
		super(msg);
	}
	
	public CTPServiceException(int sc, String msg){
		super(msg);
		this.sc = sc;
	}
}
