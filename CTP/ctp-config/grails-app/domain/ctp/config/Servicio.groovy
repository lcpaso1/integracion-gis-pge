package ctp.config

class Servicio {

	String nombre;
	String dirLogica;
	String dirFisica;

	static hasMany = [metodos:Metodo]
	
    static constraints = {
    }
	
	String toString(){
		return nombre
	} 
	
}
