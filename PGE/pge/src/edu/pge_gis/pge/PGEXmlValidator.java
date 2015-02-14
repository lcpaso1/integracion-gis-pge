package edu.pge_gis.pge;

import java.util.HashMap;

import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import edu.pge_gis.pge.utils.PGEConstants;

public class PGEXmlValidator implements ActionPipelineProcessor {

	/** OJOTA!!!!  todos los procesadores necesitan este constructor, pero hay que agregarlo a mano*/
	public PGEXmlValidator(ConfigTree config){
		
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
		// Este valida el xml que sea soap, NO ES OBLIGATORIO, por ahroa no hace nada.
		//y lo cambiamos de lugar para poder agregar mas cosas al mensaje
		//le doy la responsabilidad de parsear el xml y obtener los daots que exige la pge
		//token, rol, servicio, metodo
		String xml = new String(msg.getBody().get().toString());
		msg.getBody().add(PGEConstants.KEY_xmlSoap,xml);
		HashMap<String, String> params = new HashMap<>();
		//chanchada usar xpath
		int ini_to = xml.indexOf("<wsa:To>");
		int fin_to = xml.indexOf("</wsa:To>");
		String dir_logica = xml.substring(ini_to+8, fin_to);
		System.out.println(dir_logica);
		params.put(PGEConstants.KEY_dir_logica, dir_logica);
		//mando tambien el metodo
		ini_to = xml.indexOf("<wsa:Action>");
		fin_to = xml.indexOf("</wsa:Action>");
		params.put(PGEConstants.KEY_metodo, xml.substring(ini_to+12, fin_to));
		//parseo el rol
		params.put(PGEConstants.KEY_rol, "ou:publico");
		msg.getBody().add(PGEConstants.KEY_params, params);
		return msg;
	}

	@Override
	public void processException(Message msg, Throwable arg1) {
		// TODO Auto-generated method stub
		String msgSoap = "<env:Envelope xmlns:env='http://schemas.xmlsoap.org/soap/envelope/'><env:Header></env:Header><env:Body><env:Fault xmlns:env='http://schemas.xmlsoap.org/soap/envelope/'><faultcode>env:Server</faultcode><faultstring>"+
						arg1.getMessage()+"</faultstring></env:Fault></env:Body></env:Envelope>";
		msg.getBody().add(msgSoap);
	}

	@Override
	public void processSuccess(Message arg0) {
		// TODO Auto-generated method stub

	}

}
