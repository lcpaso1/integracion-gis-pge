package edu.pge_gis.ctp.dto;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class InfoServicio {

	public static final String INFO_SERVICIO = "servicio";
	
	public static final String NOMBRE_SERVICIO = "nombre_servicio";
	public static final String DIRECCION_LOGICA = "direccion_logica";
	public static final String DIRECCION_PROXY = "direccion_proxy";
	public static final String SERVICIO_PUBLICO = "es_servicio_publico";
	public static final String NOMBRE_METODO = "nombre_metodo";
	public static final String NOMBRE_METODO_XML = "nombre_metodo_xml";

	public static final String SEG_PUBLICA_PERFIL = "sp_perfil";
	public static final String SEG_PUBLICA_ROL = "sp_rol";
	public static final String SEG_PUBLICA_USUARIO = "sp_usuario";

	public static final String SEGURIDAD_IP = "seguridad_ip";
	public static final String SEGURIDAD_PASS = "seguridad_password";
	public static final String SEGURIDAD_ROLES = "seguridad_roles";
	public static final String SEGURIDAD_TOKEN = "seguridad_token";
	public static final String SEGURIDAD_USUARIO = "seguridad_usuario";

	
	public Map<String, String> datos = new HashMap<String, String>();
	
	public void showInfo() {
		System.out.println("**** info-servicio ****");
		for (String key : this.datos.keySet()) {
			System.out.println(key + " -> " + this.datos.get(key));
		}
		System.out.println("***********************");

	}
	
}
