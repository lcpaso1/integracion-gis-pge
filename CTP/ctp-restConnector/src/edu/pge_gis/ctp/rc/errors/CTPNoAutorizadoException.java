package edu.pge_gis.ctp.rc.errors;

public class CTPNoAutorizadoException extends RuntimeException implements HTTPStatusCode {

	public CTPNoAutorizadoException(String msg){
		super(msg);
	}

	@Override
	public int statusCode() {
		// TODO Auto-generated method stub
		return 401;
	}
	
}
