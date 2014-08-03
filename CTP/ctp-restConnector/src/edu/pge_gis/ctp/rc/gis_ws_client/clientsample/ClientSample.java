package edu.pge_gis.ctp.rc.gis_ws_client.clientsample;

import edu.pge_gis.ctp.rc.gis_ws_client.*;

public class ClientSample {

	public static void main(String[] args) {
	        System.out.println("***********************");
	        System.out.println("Create Web Service Client...");
	        GISWSwmsYwfsService service1 = new GISWSwmsYwfsService();
	        System.out.println("Create Web Service...");
	        GISWS port1 = service1.getGISWSPort();
	        System.out.println("Call Web Service Operation...");
	        System.out.println("Server said: " + port1.describeFeatureType(null));
	        //Please input the parameters instead of 'null' for the upper method!
	
	        System.out.println("Server said: " + port1.getCapabilities(null));
	        //Please input the parameters instead of 'null' for the upper method!
	
	        System.out.println("Server said: " + port1.getFeature(null));
	        //Please input the parameters instead of 'null' for the upper method!
	
	        System.out.println("Server said: " + port1.getFeatureInfo(null));
	        //Please input the parameters instead of 'null' for the upper method!
	
	        System.out.println("Server said: " + port1.getGmlObject(null));
	        //Please input the parameters instead of 'null' for the upper method!
	
	        System.out.println("Server said: " + port1.getMap(null));
	        //Please input the parameters instead of 'null' for the upper method!
	
	        System.out.println("Server said: " + port1.lockFeature(null));
	        //Please input the parameters instead of 'null' for the upper method!
	
	        System.out.println("Server said: " + port1.transaction(null));
	        //Please input the parameters instead of 'null' for the upper method!
	
	        System.out.println("***********************");
	        System.out.println("Call Over!");
	}
}
