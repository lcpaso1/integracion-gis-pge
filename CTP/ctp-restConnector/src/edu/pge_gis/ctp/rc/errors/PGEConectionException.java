package edu.pge_gis.ctp.rc.errors;

public class PGEConectionException extends RuntimeException implements HTTPStatusCode {

	@Override
	public int statusCode() {
		// TODO Auto-generated method stub
		return 502;
	}
//lanzada si no hay conectividad de la pge
	public PGEConectionException(String msg){
		super(msg);
	}
}
