package edu.pge_gis.ctp.sc.handlers;

import java.io.IOException;
import java.util.Properties;

public class PropertiesHandler {
	
	private static PropertiesHandler instance;
	private Properties prop;
	
	private PropertiesHandler(){
		prop = new Properties();
		try {
			prop.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static PropertiesHandler getInstance() {
		if (instance == null)
			instance = new PropertiesHandler();
		
		return instance;
	}
	
	public String getProperty(String name){
		return prop.getProperty(name);
	}

}
