package ctp.config

class Seguridad {

	String ip
	String usuario
	String password
	String token
	String roles
	String perfil

	static belongsTo = [servicioGis:ServicioGis]

    static constraints = {
    	servicioGis(nullable:true)
    }
}
