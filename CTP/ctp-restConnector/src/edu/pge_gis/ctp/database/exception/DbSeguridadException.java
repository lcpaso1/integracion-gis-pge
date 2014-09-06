package edu.pge_gis.ctp.database.exception;

public class DbSeguridadException extends Exception {

	private static final long serialVersionUID = 3343193814376171958L;

	public DbSeguridadException() {
		super();
	}

	public DbSeguridadException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DbSeguridadException(String message, Throwable cause) {
		super(message, cause);
	}

	public DbSeguridadException(String message) {
		super(message);
	}

	public DbSeguridadException(Throwable cause) {
		super(cause);
	}

}
