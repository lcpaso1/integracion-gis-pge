package edu.pge_gis.ctp.database.dominio;

import java.util.ArrayList;
import java.util.List;

public class ServicioGis {

	private int id;
	private String nombre;
	private String direccionLogica;
	private String direccionProxy;
	private boolean publico;
	
	private List<Metodo> metodos = null;
	
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

	public List<Metodo> getMetodos() {
		if (metodos==null) {
			metodos = new ArrayList<Metodo>();
		}
		return metodos;
	}

	public void setMetodos(List<Metodo> metodos) {
		this.metodos = metodos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
