package edu.pge_gis.ctp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class SeguridadRepo {	
	
	public static ServicioGis getServicioGIS(String identificador) throws SQLException {
		String url = "jdbc:postgresql://localhost/ctpconfig";
		Properties props = new Properties();
		props.setProperty("user","postgres");
		props.setProperty("password","admin");
		Connection conn = DriverManager.getConnection(url, props);
		
		String sql = "Select * from Servicio_Gis where nombre='"+identificador+"'";
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		
		if (rs.next()) {
			System.out.println("nombre " + rs.getString("nombre"));
			System.out.println("dir_proxy " + rs.getString("direccion_proxy"));
			System.out.println("dir_logica " + rs.getString("direccion_logica"));
			System.out.println("publico " + rs.getBoolean("publico"));
			ServicioGis servicio = new ServicioGis();
			servicio.setNombre(identificador);
			servicio.setDireccionLogica(rs.getString("direccion_logica"));
			servicio.setDireccionProxy(rs.getString("direccion_proxy"));
			servicio.setPublico(rs.getBoolean("publico"));
			return servicio;
		}
		
		return null;
		
	}
	
	
	public static void main(String[] args) throws Exception {
		getServicioGIS("meteoro");
	}
	
}


