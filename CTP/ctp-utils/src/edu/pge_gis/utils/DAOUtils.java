package edu.pge_gis.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class DAOUtils {

	private static Map<String,Connection> conns = new HashMap<String, Connection>();
	
	public static Connection getConnection(String url, String usr, String pwd) throws SQLException{
		Connection conn = conns.get(url);
		
		if(conn != null) return conn;
		// Load the database driver
	      try {
			Class.forName( "org.postgresql.Driver" ) ;
			// Get a connection to the database
			//jdbc:postgresql:database
		    conn = DriverManager.getConnection( url,usr,pwd ) ;
		    conns.put(url,conn);
		    return conn;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new SQLException(e);
		} 
	}
	
	public static Statement prepareStatement(String url, String usr, String pwd) throws SQLException{

		Connection conn = getConnection(url, usr, pwd);
		Statement stmt = conn.createStatement() ;
		return stmt;
	}
	
	
}
