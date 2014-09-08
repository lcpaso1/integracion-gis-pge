package edu.pge_gis.pge;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import edu.pge_gis.utils.DAOUtils;

public class SQLUtils {

	public static HashMap<String, String> getServicio(String dir_logica){
		try {
			Statement stm =DAOUtils.prepareStatement("jdbc:postgresql:pge-conf", "postgres", "admin");
			ResultSet rs =stm.executeQuery("select * from servicio where dir_logica='"+dir_logica+"';");
			if(rs.next()){
				HashMap<String, String> serv = new HashMap<>();
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
	
	
}
