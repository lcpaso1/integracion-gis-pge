package edu.pge_gis.pge;

import java.util.HashMap;

import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

public class PGECatalog implements ActionPipelineProcessor {

	/** OJOTA!!!!  todos los procesadores necesitan este constructor, pero hay que agregarlo a mano*/
	public PGECatalog(ConfigTree config){
		
	}
	@Override
	public void destroy() throws ActionLifecycleException {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialise() throws ActionLifecycleException {
		// TODO Auto-generated method stub

	}

	@Override
	public Message process(Message msg) throws ActionProcessingException {
		// Este define el proveedor y agrega o quita cabezales ws-addressing del xml soap
		//en base a los cabezales addressing, sacamos la url del proveedor
		String xml = msg.getBody().get("xmlSoap").toString();
		System.out.println(xml);
		//chanchada usar xpath
		int ini_to = xml.indexOf("<wsa:To>");
		int fin_to = xml.indexOf("</wsa:To>");
		String dir_logica = xml.substring(ini_to+8, fin_to);
		System.out.println(dir_logica);
		HashMap<String, String> serv = SQLUtils.getServicio(dir_logica);
		System.out.println(serv.get("urlProvider"));
		//mando tambien el metodo
		ini_to = xml.indexOf("<wsa:Action>");
		fin_to = xml.indexOf("</wsa:Action>");
		serv.put("metodo", xml.substring(ini_to+12, fin_to));
		//obtener metodo
		//por ahora solo agrego una
		msg.getBody().add("servicio", serv);
		return msg;
	}

	@Override
	public void processException(Message arg0, Throwable arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processSuccess(Message arg0) {
		// TODO Auto-generated method stub

	}

}
