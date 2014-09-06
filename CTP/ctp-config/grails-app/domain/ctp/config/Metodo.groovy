package ctp.config

class Metodo {

	String nombre
	String nombreXml

	static belongsTo = [seguridad:Seguridad]

    static constraints = {
    }
	
	String toString(){
		return nombre
	} 
}
