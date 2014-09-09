package ctp.config

class Metodo {

	String nombre
	String nombreXml

	static belongsTo = [seguridad:Seguridad, servicioGis:ServicioGis]

    static constraints = {
    	seguridad(nullable:true)
    }
	
	String toString(){
		return nombre
	} 
}
