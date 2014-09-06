package ctp.config

class ConfSeguridadPublica {

	String usuario
	String rol
	String perfil

	static belongsTo = [servicioGis:ServicioGis]

    static constraints = {
    	servicioGis(nullable:true)
    }

    String toString(){
		return usuario + " " + perfil
	} 
}
