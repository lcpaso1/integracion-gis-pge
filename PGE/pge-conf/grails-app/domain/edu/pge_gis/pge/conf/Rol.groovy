package edu.pge_gis.pge.conf

class Rol {

	String nombre
	static hasMany=[perfiles:Perfil]
	
    static constraints = {
    }
}
