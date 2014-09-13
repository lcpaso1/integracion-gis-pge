package edu.pge_gis.ctp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import edu.pge_gis.ctp.database.dominio.ConfSeguridadPublica;
import edu.pge_gis.ctp.database.dominio.Metodo;
import edu.pge_gis.ctp.database.dominio.Seguridad;
import edu.pge_gis.ctp.database.dominio.ServicioGis;
import edu.pge_gis.utils.DAOUtils;

public class SeguridadRepo {	
	
	private static String DATABASE_URL = "jdbc:postgresql://localhost/ctpconfig";
	private static String DATABASE_USER = "postgres";
	private static String DATABASE_PASS = "admin";
	
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
			
			String metnombre = rs.getString("metnombre");
			if (metnombre!=null) {
				Metodo metodo = new Metodo();
				metodo.setNombre(metnombre);
				metodo.setNombreXml(rs.getString("nombre_xml"));
				servicio.getMetodos().add(metodo);
			}
			
			
		}
		
		return servicio;
		
	}
	
	public static Seguridad getSeguridad(String ip) throws SQLException{
		String sql = "Select * from Seguridad where ip='" + ip + "'";
		Statement statement = DAOUtils.prepareStatement(DATABASE_URL, DATABASE_USER, DATABASE_PASS);
		ResultSet rs = statement.executeQuery(sql);
		
		if (rs.next()) {
			Seguridad seguridad = new Seguridad();
			seguridad.setIp(ip);
			seguridad.setPassword(rs.getString("password"));
			seguridad.setPerfil(rs.getString("perfil"));
			seguridad.setRoles(rs.getString("roles"));
			seguridad.setToken(rs.getString("token"));
			seguridad.setUsuario(rs.getString("usuario"));
			return seguridad;
		}
		
		return null;
	}
	
	
	public static ConfSeguridadPublica getSeguridadPublicaParaServicio(String nombreServicio) throws SQLException{
		String sql = "select c.perfil, c.rol, c.usuario from conf_seguridad_publica c join servicio_gis s on c.servicio_gis_id=s.id where s.nombre='" + nombreServicio + "'";
		Statement statement = DAOUtils.prepareStatement(DATABASE_URL, DATABASE_USER, DATABASE_PASS);
		ResultSet rs = statement.executeQuery(sql);
		
		if (rs.next()) {
			ConfSeguridadPublica seguridad = new ConfSeguridadPublica();
			seguridad.setRol(rs.getString("rol"));
			seguridad.setUsuario(rs.getString("usuario"));
			seguridad.setPerfil(rs.getString("perfil"));
			return seguridad;
		}
		
		return null;
	
		
	}
	
	public static void main(String[] args) throws Exception {


	}
	
}


