package edu.pge_gis.ctp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.hibernate.property.MapAccessor.MapSetter;

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
		ServicioGis servicio = null;
		while (rs.next()) {

			if (servicio == null) {
				servicio = new ServicioGis();
				servicio.setNombre(identificador);
				servicio.setDireccionLogica(rs.getString("direccion_logica"));
				servicio.setDireccionProxy(rs.getString("direccion_proxy"));
				servicio.setPublico(rs.getBoolean("publico"));
				servicio.setId(rs.getInt("id"));
			}
			
			if (servicio!=null) {
				String metnombre = rs.getString("metnombre");
				if (metnombre!=null) {
					Metodo metodo = new Metodo();
					metodo.setNombre(metnombre);
					metodo.setNombreXml(rs.getString("nombre_xml"));
					metodo.setId(rs.getInt("metid"));
					servicio.getMetodos().add(metodo);
				}
			}
		}
		
		if (servicio == null ) {
			throw new SQLException("No existe el servicio '" + identificador + "'");
		}
		
		return servicio;
		
	}

	public static Seguridad getSeguridad(String ip, int idServicio, int idMetodo) throws SQLException{
		String sql = "Select s.* from Seguridad s join metodo m on m.seguridad_id=s.id where s.ip='" + ip + "' and s.servicio_gis_id=" + idServicio + "and m.id=" + idMetodo;
		System.out.println(" SeguridadRepo::getSeguridad : '" + sql + "'");
		Statement statement = DAOUtils.prepareStatement(DATABASE_URL, DATABASE_USER, DATABASE_PASS);
		ResultSet rs = statement.executeQuery(sql);
		
		if (rs.next()) {
			Seguridad seguridad = mapSeguridad(rs);
			return seguridad;
		} else {
			sql =  "Select * from Seguridad where ip='" + ip + "' and servicio_gis_id="+idServicio ; 
			rs = statement.executeQuery(sql);
			if (rs.next()) {
				Seguridad seguridad = mapSeguridad(rs);
				return seguridad;
			}
		}
		
		throw new SQLException("No se encontró seguridad configurada para IP:" + ip + " servicio:" + idServicio + " y metodo:" + idMetodo);
	}

	private static Seguridad mapSeguridad(ResultSet rs) throws SQLException {
		Seguridad seguridad = new Seguridad();
		seguridad.setIp(rs.getString("ip"));
		seguridad.setPassword(rs.getString("password"));
		seguridad.setPerfil(rs.getString("perfil"));
		seguridad.setRoles(rs.getString("roles"));
		seguridad.setToken(rs.getString("token"));
		seguridad.setUsuario(rs.getString("usuario"));
		return seguridad;
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


