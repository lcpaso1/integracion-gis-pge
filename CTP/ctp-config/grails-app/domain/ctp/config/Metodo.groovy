package ctp.config

class Metodo {

	String nombre;

    static hasMany = [perfiles:Perfil]

    static constraints = {
    }
	
	String toString(){
		return nombre
	} 
}
