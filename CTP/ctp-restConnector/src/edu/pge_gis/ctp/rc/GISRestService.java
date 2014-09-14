package edu.pge_gis.ctp.rc;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.http.HttpRequest;
import org.jboss.soa.esb.message.Message;

import edu.pge_gis.ctp.database.SeguridadRepo;
import edu.pge_gis.ctp.database.dominio.ConfSeguridadPublica;
import edu.pge_gis.ctp.database.dominio.Metodo;
import edu.pge_gis.ctp.database.dominio.Seguridad;
import edu.pge_gis.ctp.database.dominio.ServicioGis;
import edu.pge_gis.ctp.dto.InfoServicio;
import edu.pge_gis.ctp.rc.gis_ws_client.GisParams;
import static edu.pge_gis.ctp.dto.InfoServicio.*;

public class GISRestService implements ActionPipelineProcessor {

	/** OJOTA!!!!  todos los procesadores necesitan este constructor, pero hay que agregarlo a mano*/
	public GISRestService(ConfigTree config){
		
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
		// Este toma el request http y carga los parametros en el mensaje para usarlos a posteriori
		// obtener ip, sevicio, metodo
		// to=dir logica, action=metodo a partir de lo anterior 
		HttpRequest request = HttpRequest.getRequest(msg);
		GisParams params = parsearParametros(request);
		
		msg.getBody().add("params",params);
		
		String[] uri = request.getRequestURI().split("/");
		/**DEFINIMOS QUE los dos ultimos son el metodo y el servicio*/
		//servidor:puerto/ctp-restConnector/http/ctp/SERVICIO/METODO
		//localhost:8080/ctp-restConnector/http/ctp/meteorologia/getMap
		
		// obtener el nombre del servicio como el ultimo token en la url.
		String nombreServicio = uri[uri.length-1];
		
		InfoServicio datosServicio = new InfoServicio();
		
		try {
			// obtener los datos del servicio llamando a la bd.
			ServicioGis servicio = SeguridadRepo.getServicioGISConMetodos(nombreServicio);
			// guardar los datos en un mapa.
			datosServicio.datos.put(NOMBRE_SERVICIO, servicio.getNombre());
			datosServicio.datos.put(DIRECCION_LOGICA, servicio.getDireccionLogica());
			datosServicio.datos.put(DIRECCION_PROXY, servicio.getDireccionProxy());
			datosServicio.datos.put(SERVICIO_PUBLICO, String.valueOf(servicio.isPublico()));

			String nombreMetodo = params.getRequest().toLowerCase();
			Metodo metodoSeleccionado = null;
			for (Metodo metodo : servicio.getMetodos() ) {
				if (metodo.getNombre().equals(nombreMetodo)) {
					metodoSeleccionado = metodo;
				}
			}
			if (metodoSeleccionado==null) {
				String errmsg = nombreMetodo==null ? "No especificó método en la url" : "Método '" + nombreMetodo + "' desconocido.";
				throw new ActionProcessingException(errmsg);
			} else {
				datosServicio.datos.put(NOMBRE_METODO, nombreMetodo);
				datosServicio.datos.put(NOMBRE_METODO_XML, metodoSeleccionado.getNombreXml());
			}
			
			// obtener datos de seguridad a partid de la IP, si es publico hay que devolver ConfSeguridadPublica
			String requestIp = request.getRemoteAddr();
			if (servicio.isPublico()) {
				ConfSeguridadPublica sp = SeguridadRepo.getSeguridadPublicaParaServicio(nombreServicio);
				System.out.println("Seguridad publica : " + sp.getPerfil() + " , " + sp.getRol() + " , " + sp.getUsuario()); 
				// mapear seguridad publica
				datosServicio.datos.put(SEG_PUBLICA_PERFIL, sp.getPerfil());
				datosServicio.datos.put(SEG_PUBLICA_ROL, sp.getRol());
				datosServicio.datos.put(SEG_PUBLICA_USUARIO, sp.getUsuario());
			} else {
				Seguridad seguridad = SeguridadRepo.getSeguridad(requestIp);
				// mapear seguridad
				datosServicio.datos.put(SEGURIDAD_IP, seguridad.getIp());
				datosServicio.datos.put(SEGURIDAD_PASS, seguridad.getPassword());
				datosServicio.datos.put(SEGURIDAD_ROLES, seguridad.getRoles());
				datosServicio.datos.put(SEGURIDAD_TOKEN, seguridad.getToken());
				datosServicio.datos.put(SEGURIDAD_USUARIO, seguridad.getUsuario());
			}
			
			// incluir el mapa en el body
			msg.getBody().add(INFO_SERVICIO,datosServicio);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ActionProcessingException("Error al acceder al repositorio de seguridad.");
		}
		
		return msg;
	}

	private GisParams parsearParametros(HttpRequest request) {
		//cambiamos claves a minusculas
		Map<String, String[]> qsMinusculas = new HashMap<>();
		for(Entry<String, String[]> e : request.getQueryParams().entrySet()){
			qsMinusculas.put(e.getKey().toLowerCase(), e.getValue());
		}
		System.out.println(qsMinusculas);
		/////
		if (qsMinusculas.get("request")[0].toString().equalsIgnoreCase("GetMap"))
			return getMap(request,qsMinusculas);
		else if (qsMinusculas.get("request")[0].toString().equalsIgnoreCase("GetCapabilities"))
			return getCapabilities(request,qsMinusculas);
		else if (qsMinusculas.get("request")[0].toString().equalsIgnoreCase("GetFeatureInfo"))
			return getFeatureInfo(request,qsMinusculas);
		else if (qsMinusculas.get("request")[0].toString().equalsIgnoreCase("DescribeFeatureType"))
			return describeFeatureType(request,qsMinusculas);
		else if (qsMinusculas.get("request")[0].toString().equalsIgnoreCase("GetFeature"))
			return getFeature(request,qsMinusculas);
		else if (qsMinusculas.get("request")[0].toString().equalsIgnoreCase("GetGmlObject"))
			return getGmlObject(request,qsMinusculas);
		else if (qsMinusculas.get("request")[0].toString().equalsIgnoreCase("Transaction"))
			return transaction(request,qsMinusculas);
		else if (qsMinusculas.get("request")[0].toString().equalsIgnoreCase("LockFeature"))
			return lockFeature(request,qsMinusculas);
		else
			return null;
	}
	@Override
	public void processException(Message arg0, Throwable arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processSuccess(Message arg0) {
		// TODO Auto-generated method stub

	}
	
	private GisParams createParams(String service, String method, String parameters){
		GisParams params = new GisParams();
		params.setRequest(method);
		params.setService(service);
		params.setParams(parameters);
		
		return params;
	}
	
	private GisParams getMap(HttpRequest request, Map<String, String[]> qsMinusculas){
		return createParams(qsMinusculas.get("service")[0].toString(), 
				qsMinusculas.get("request")[0], 
				request.getQueryString());
	}
	
	private GisParams getCapabilities(HttpRequest request, Map<String, String[]> qsMinusculas){
		return createParams(qsMinusculas.get("service")[0].toString(), 
				qsMinusculas.get("request")[0], 
				request.getQueryString());
	}
	
	private GisParams getFeatureInfo(HttpRequest request, Map<String, String[]> qsMinusculas){
		return createParams(qsMinusculas.get("service")[0].toString(), 
				qsMinusculas.get("request")[0], 
				request.getQueryString());
	}
	
	private GisParams describeFeatureType(HttpRequest request, Map<String, String[]> qsMinusculas){
		return createParams(qsMinusculas.get("service")[0].toString(), 
				qsMinusculas.get("request")[0], 
				request.getQueryString());
	}

	private GisParams getFeature(HttpRequest request, Map<String, String[]> qsMinusculas){
		return createParams(qsMinusculas.get("service")[0].toString(), 
				qsMinusculas.get("request")[0], 
				request.getQueryString());
	}
	
	private GisParams getGmlObject(HttpRequest request, Map<String, String[]> qsMinusculas){
		return createParams(qsMinusculas.get("service")[0].toString(), 
				qsMinusculas.get("request")[0], 
				request.getQueryString());
	}
	
	private GisParams transaction(HttpRequest request, Map<String, String[]> qsMinusculas){
		return createParams(qsMinusculas.get("service")[0].toString(), 
				qsMinusculas.get("request")[0], 
				request.getQueryString());
	}
	
	private GisParams lockFeature(HttpRequest request, Map<String, String[]> qsMinusculas){
		return createParams(qsMinusculas.get("service")[0].toString(), 
				qsMinusculas.get("request")[0], 
				request.getQueryString());
	}
}
