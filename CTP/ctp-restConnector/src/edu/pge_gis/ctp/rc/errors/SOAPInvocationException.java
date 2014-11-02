package edu.pge_gis.ctp.rc.errors;

public class SOAPInvocationException extends RuntimeException implements
		HTTPStatusCode {

	@Override
	public int statusCode() {
		// TODO Auto-generated method stub
		return 500;
	}

	public SOAPInvocationException(String msg){
		super(msg);
	}
}
