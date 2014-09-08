package edu.pge_gis.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOUtils {

	private static Connection conn = null;
	
	public static Connection getConnection(String url, String usr, String pwd) throws SQLException{
		if(conn != null) return conn;
		// Load the database driver
	      try {
			Class.forName( "org.postgresql.Driver" ) ;
			// Get a connection to the database
			//jdbc:postgresql:database
		    conn = DriverManager.getConnection( url,usr,pwd ) ;
		    return conn;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
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
