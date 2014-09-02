package ctp.config

class Perfil {

	String nombre;

	static belongsTo = [rol:Rol]
	
    static constraints = {
    }
	
	String toString(){
		return nombre
	} 	
}
