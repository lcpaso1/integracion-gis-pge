package edu.pge_gis.ctp.database.dominio;

public class Metodo {

	private int id;
	private String nombre;
	private String nombreXml;
	
	public Metodo(){
		
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreXml() {
		return nombreXml;
	}

	public void setNombreXml(String nombreXml) {
		this.nombreXml = nombreXml;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
