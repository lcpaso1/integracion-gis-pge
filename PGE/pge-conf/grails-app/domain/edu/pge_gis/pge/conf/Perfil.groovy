package edu.pge_gis.pge.conf

class Perfil {

	String nombre
	static hasMany = [metodos:Metodo]
    static constraints = {
    }
}
