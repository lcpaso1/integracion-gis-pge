package edu.pge_gis.ctp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import edu.pge_gis.ctp.database.dominio.Metodo;
import edu.pge_gis.ctp.database.dominio.ServicioGis;
import edu.pge_gis.utils.DAOUtils;

public class SeguridadRepo {	
	
	private static String DATABASE_URL = "jdbc:postgresql://localhost/ctpconfig";
	private static String DATABASE_USER = "postgres";
	private static String DATABASE_PASS = "admin";
	
	public static ServicioGis getServicioGIS(String identificador) throws SQLException {
//		String url = "jdbc:postgresql://localhost/ctpconfig";
//		Properties props = new Properties();
//		props.setProperty("user","postgres");
//		props.setProperty("password","admin");
//		Connection conn = DriverManager.getConnection(url, props);
//		Statement statement = conn.createStatement();

		String sql = "Select * from Servicio_Gis where nombre='"+identificador+"'";
		Statement statement = DAOUtils.prepareStatement(DATABASE_URL, DATABASE_USER, DATABASE_PASS);
		
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
	
	
	public static ServicioGis getServicioGISConMetodos(String identificador) throws SQLException {

		String sql = "Select g.id, g.version, g.direccion_logica, g.direccion_proxy, g.nombre, g.publico,m.id as metid, m.nombre as metnombre, m.nombre_xml "
				+ "from Servicio_Gis g left outer join metodo m on m.servicio_gis_id=g.id where g.nombre='"+identificador+"'";
		Statement statement = DAOUtils.prepareStatement(DATABASE_URL, DATABASE_USER, DATABASE_PASS);
		
		ResultSet rs = statement.executeQuery(sql);
		ServicioGis servicio = new ServicioGis();
		while (rs.next()) {
			System.out.println("nombre " + rs.getString("nombre"));
			System.out.println("dir_proxy " + rs.getString("direccion_proxy"));
			System.out.println("dir_logica " + rs.getString("direccion_logica"));
			System.out.println("publico " + rs.getBoolean("publico"));
			servicio.setNombre(identificador);
			servicio.setDireccionLogica(rs.getString("direccion_logica"));
			servicio.setDireccionProxy(rs.getString("direccion_proxy"));
			servicio.setPublico(rs.getBoolean("publico"));
			
			Integer metodoId = rs.getInt("metid");
			if (metodoId!=null) {
				Metodo metodo = new Metodo();
				metodo.setNombre(rs.getString("metnombre"));
				metodo.setNombre(rs.getString("nombre_xml"));
				servicio.getMetodos().add(metodo);
			}
			
			return servicio;
		}
		
		return null;
		
	}
	
	public static void main(String[] args) throws Exception {
		getServicioGIS("meteoro");
	}
	
}


