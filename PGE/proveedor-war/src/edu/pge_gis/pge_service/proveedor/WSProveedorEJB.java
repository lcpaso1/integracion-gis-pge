package edu.pge_gis.pge_service.proveedor;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Session Bean implementation class WSProveedorEJB
 */
@WebService(name="WSProveedor")
public class WSProveedorEJB {

    /**
     * Default constructor. 
     */
    public WSProveedorEJB() {
        // TODO Auto-generated constructor stub
    }
    
    @WebMethod
    public int sumar(@WebParam(name="a")int a, @WebParam(name="b")int b){
    	System.out.println("llega a:"+a);
    	return a+b;
    }

}
