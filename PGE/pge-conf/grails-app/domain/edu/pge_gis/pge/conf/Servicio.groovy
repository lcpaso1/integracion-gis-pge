package edu.pge_gis.pge.conf

class Servicio {

	String nombre
	String dirLogica
	String dirFisica
	
    static constraints = {
		nombre(unique:true)
		dirLogica(unique:true)
    }
}
