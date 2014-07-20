package edu.pge_gis.pge_service.ws.client.clientsample;

import edu.pge_gis.pge_service.ws.client.*;

public class ClientSample {

	public static void main(String[] args) {
	        System.out.println("***********************");
	        System.out.println("Create Web Service Client...");
	        WSProveedorEJBService service1 = new WSProveedorEJBService();
	        System.out.println("Create Web Service...");
	        WSProveedor port1 = service1.getWSProveedorPort();
	        System.out.println("Call Web Service Operation...");
	        System.out.println("Server said: " + port1.sumar(Integer.parseInt(args[0]),Integer.parseInt(args[1])));
	        System.out.println("***********************");
	        System.out.println("Call Over!");
	}
}
