package edu.pge_gis.ctp.rc.errors;

public class CTPServiceException extends RuntimeException implements HTTPStatusCode {

	@Override
	public int statusCode() {
		// TODO Auto-generated method stub
		return 404;
	}
	//exception que será lanzada dentro de este ctp, si hay algun problema de configuración o configuracion con la pge.
}
