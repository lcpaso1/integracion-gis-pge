package edu.pge_gis.pge;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import edu.pge_gis.utils.DAOUtils;

public class SQLUtils {

	private static Statement getStm() throws SQLException{
		return DAOUtils.prepareStatement("jdbc:postgresql:pge-conf", "postgres", "admin");
	}
	
	public static HashMap<String, String> getServicio(String dir_logica){
		try {
			Statement stm = getStm();
			ResultSet rs =stm.executeQuery("select * from servicio where dir_logica='"+dir_logica+"';");
			if(rs.next()){
				HashMap<String, String> serv = new HashMap<>();
				serv.put("id", rs.getString("id"));
				serv.put("nombre", rs.getString("nombre"));
				serv.put("urlProvider", rs.getString("dir_fisica"));
				serv.put("dir_logica", rs.getString("dir_logica"));
				return serv;
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static HashMap<String, String> getMetodo(String id, String metodo){
		try {
			Statement stm = getStm();
			ResultSet rs =stm.executeQuery("select * from metodo where nombre='"+metodo+"' and servicio_id = "+id+";");
			if(rs.next()){
				HashMap<String, String> serv = new HashMap<>();
				serv.put("id", rs.getString("id"));
				serv.put("nombre", rs.getString("nombre"));
				return serv;
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static long getIdRol(String rol){
		try {
			Statement stm = getStm();
			ResultSet rs =stm.executeQuery("select id from rol where nombre='"+rol+"' ;");
			if(rs.next()){
				return rs.getLong("id");
			}
			return 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	public static boolean tienePermiso(String idrol,String idmetodo){
		try {
			Statement stm = getStm();
			ResultSet rs =stm.executeQuery("select p.* from perfil p, rol_perfil rp, perfil_metodo pm where "+
											"p.id = rp.perfil_id and "+
											"pm.perfil_metodos_id = p.id and "+
											"rp.rol_perfiles_id = "+idrol+" and "+
											"pm.metodo_id = "+idmetodo+" ;");
			return rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
}
