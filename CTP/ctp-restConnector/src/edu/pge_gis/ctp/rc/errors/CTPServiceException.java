package edu.pge_gis.ctp.rc.errors;

public class CTPServiceException extends RuntimeException implements HTTPStatusCode {

	@Override
	public int statusCode() {
		// TODO Auto-generated method stub
		return 404;
	}
	//exception que ser� lanzada dentro de este ctp, si hay algun problema de configuraci�n o configuracion con la pge.
}
