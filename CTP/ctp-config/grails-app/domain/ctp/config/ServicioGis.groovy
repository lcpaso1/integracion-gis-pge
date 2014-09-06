package ctp.config

class ServicioGis {

	String nombre
	String direccionLogica
	String direccionProxy
	boolean publico  // ??? que es este campo? booleano?

	static hasMany = [seguridad:Seguridad] 

    static constraints = {
    }

    String toString(){
		return nombre;
	} 
}
