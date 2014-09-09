package edu.pge_gis.ctp.database;

public class ServicioGis {

	private String nombre;
	private String direccionLogica;
	private String direccionProxy;
	private boolean publico;
	
	public ServicioGis(){
		
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccionLogica() {
		return direccionLogica;
	}

	public void setDireccionLogica(String direccionLogica) {
		this.direccionLogica = direccionLogica;
	}

	public String getDireccionProxy() {
		return direccionProxy;
	}

	public void setDireccionProxy(String direccionProxy) {
		this.direccionProxy = direccionProxy;
	}

	public boolean isPublico() {
		return publico;
	}

	public void setPublico(boolean publico) {
		this.publico = publico;
	}
	
	
}
