package edu.pge_gis.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PropertiesUtils {

	private static boolean cargadas = false;
			
	private static void cargarProps() throws FileNotFoundException, IOException{
		File f;
		if(!cargadas){
			f = new File("../server/default/conf/ctp-config.properties");
			//System.out.println(f.getAbsolutePath());
			System.getProperties().load(new FileInputStream(f));
			cargadas=true;
		}
	}
	
	public static String getProp(String prop, String defaultvalue){
		try {
			cargarProps();
			return System.getProperty(prop, defaultvalue);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return defaultvalue;
	}
	
}
