package edu.pge_gis.ctp.rc;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.http.HttpHeader;
import org.jboss.soa.esb.http.HttpRequest;
import org.jboss.soa.esb.message.Message;

import edu.pge_gis.ctp.database.SeguridadRepo;
import edu.pge_gis.ctp.database.dominio.ConfSeguridadPublica;
import edu.pge_gis.ctp.database.dominio.Metodo;
import edu.pge_gis.ctp.database.dominio.Seguridad;
import edu.pge_gis.ctp.database.dominio.ServicioGis;
import edu.pge_gis.ctp.dto.InfoServicio;
import edu.pge_gis.ctp.rc.errors.CTPServiceException;
import edu.pge_gis.ctp.rc.gis_ws_client.GisParams;
import edu.pge_gis.utils.PropertiesUtils;
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

	//TODO cargar de property
	private final static String ctp_endpointURL = "http://localhost:8080/ctp/http/ctp";
	
	@Override
	public Message process(Message msg) throws ActionProcessingException {
		// Este toma el request http y carga los parametros en el mensaje para usarlos a posteriori
		// obtener ip, sevicio, metodo
		// to=dir logica, action=metodo a partir de lo anterior 
		HttpRequest request = HttpRequest.getRequest(msg);
		
//		System.out.println("=========================================================================================");
//		for(HttpHeader hh : request.getHeaders()){
//			System.out.println(hh.getName()+": "+hh.getValue());
//		}
//		System.out.println("=========================================================================================");
		
		GisParams params = parsearParametros(request, msg);
		
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

			//datosServicio.showInfo();
			
			String nombreMetodo = params.getRequest().toLowerCase();

			Metodo metodoSeleccionado = null;
			for (Metodo metodo : servicio.getMetodos() ) {
				if (metodo.getNombre().equals(nombreMetodo)) {
					metodoSeleccionado = metodo;
				}
			}
			if (metodoSeleccionado==null) {
				String errmsg = nombreMetodo==null ? "No especificó método en la url" : "Método '" + nombreMetodo + "' desconocido.";
				throw new CTPServiceException(errmsg);
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
				Seguridad seguridad = SeguridadRepo.getSeguridad(requestIp, servicio.getId(), metodoSeleccionado.getId());
				// mapear seguridad
				datosServicio.datos.put(SEGURIDAD_IP, seguridad.getIp());
				datosServicio.datos.put(SEGURIDAD_PASS, seguridad.getPassword());
				datosServicio.datos.put(SEGURIDAD_ROLES, seguridad.getRoles());
				datosServicio.datos.put(SEGURIDAD_TOKEN, seguridad.getToken());
				datosServicio.datos.put(SEGURIDAD_USUARIO, seguridad.getUsuario());
			}
			
			datosServicio.showInfo();
			
			// incluir el mapa en el body
			msg.getBody().add(INFO_SERVICIO,datosServicio);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CTPServiceException(500,"Error al acceder al repositorio de seguridad. ");
		}
		
		return msg;
	}

	final String POST = "POST";
	
	private GisParams parsearParametros(HttpRequest request, Message msg) {
		//cambiamos claves a minusculas
		try{
			//obtengo el servicio de la url
			String[] uri = request.getRequestURI().split("/");
			String nombreServicio = uri[uri.length-1];
			//formateo los parametros
			Map<String, String[]> qsMinusculas = new HashMap<>();
			for(Entry<String, String[]> e : request.getQueryParams().entrySet()){
				qsMinusculas.put(e.getKey().toLowerCase(), e.getValue());
			}
			//primero controlo el metodo http			
			if(request.getMethod().equalsIgnoreCase(POST)){
				//asumo que el request no viene en los parametros
				String postBody = msg.getBody().get().toString();
				String metodo = parseWFSMethod(postBody,qsMinusculas);
				GisParams params = createPostParams(qsMinusculas.get("service")[0].toString(), metodo, 
						request.getQueryString(), nombreServicio, postBody);
				return params;
			}
			else{
				
				if (qsMinusculas.get("request")[0].toString().equalsIgnoreCase("GetMap"))
					return getMap(request,qsMinusculas,nombreServicio);
				else if (qsMinusculas.get("request")[0].toString().equalsIgnoreCase("GetCapabilities"))
					return getCapabilities(request,qsMinusculas,nombreServicio);
				else if (qsMinusculas.get("request")[0].toString().equalsIgnoreCase("GetFeatureInfo"))
					return getFeatureInfo(request,qsMinusculas,nombreServicio);
				else if (qsMinusculas.get("request")[0].toString().equalsIgnoreCase("DescribeFeatureType"))
					return describeFeatureType(request,qsMinusculas,nombreServicio);
				else if (qsMinusculas.get("request")[0].toString().equalsIgnoreCase("GetFeature"))
					return getFeature(request,qsMinusculas,nombreServicio);
				else if (qsMinusculas.get("request")[0].toString().equalsIgnoreCase("GetGmlObject"))
					return getGmlObject(request,qsMinusculas,nombreServicio);
				/*else if (qsMinusculas.get("request")[0].toString().equalsIgnoreCase("Transaction"))
					return transaction(request,qsMinusculas,nombreServicio);*/
				else if (qsMinusculas.get("request")[0].toString().equalsIgnoreCase("LockFeature"))
					return lockFeature(request,qsMinusculas,nombreServicio);
				else
					return null;
			}
		}catch (Exception e) {
			//bad request
			throw new CTPServiceException(400,"Error en la invocacion, faltan parametros obligatorios");
		}
	}
	private String parseWFSMethod(String postBody,
			Map<String, String[]> qsMinusculas) {
		String[] reqparam = qsMinusculas.get("request");
		if(reqparam != null && reqparam.length > 0)
			return reqparam[0];
		if(postBody.startsWith("<Transaction") || postBody.startsWith("<transaction"))
			return "transaction";
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
	
	private GisParams createPostParams(String service, String method, String parameters, String nombreServicio, String postBody){
		GisParams p = createParams(service, method, parameters, nombreServicio);
		p.setMetodoHTTP(POST);
		p.setXmlParam(postBody);
		return p;
	}
	
	private GisParams createParams(String service, String method, String parameters, String nombreServicio){
		GisParams params = new GisParams();
		params.setRequest(method);
		params.setService(service);
		params.setParams(parameters);
		params.setUrlReplace(PropertiesUtils.getProp("url.ctp.rc", ctp_endpointURL)+"/"+nombreServicio);
		
		return params;
	}
	
	private GisParams getMap(HttpRequest request, Map<String, String[]> qsMinusculas, String nombreServicio){
		return createParams(qsMinusculas.get("service")[0].toString(), 
				qsMinusculas.get("request")[0], 
				request.getQueryString(),
				nombreServicio);
	}
	
	private GisParams getCapabilities(HttpRequest request, Map<String, String[]> qsMinusculas, String nombreServicio){
		return createParams(qsMinusculas.get("service")[0].toString(), 
				qsMinusculas.get("request")[0], 
				request.getQueryString(),
				nombreServicio);
	}
	
	private GisParams getFeatureInfo(HttpRequest request, Map<String, String[]> qsMinusculas, String nombreServicio){
		return createParams(qsMinusculas.get("service")[0].toString(), 
				qsMinusculas.get("request")[0], 
				request.getQueryString(),
				nombreServicio);
	}
	
	private GisParams describeFeatureType(HttpRequest request, Map<String, String[]> qsMinusculas, String nombreServicio){
		return createParams(qsMinusculas.get("service")[0].toString(), 
				qsMinusculas.get("request")[0], 
				request.getQueryString(),
				nombreServicio);
	}

	private GisParams getFeature(HttpRequest request, Map<String, String[]> qsMinusculas, String nombreServicio){
		return createParams(qsMinusculas.get("service")[0].toString(), 
				qsMinusculas.get("request")[0], 
				request.getQueryString(),
				nombreServicio);
	}
	
	private GisParams getGmlObject(HttpRequest request, Map<String, String[]> qsMinusculas, String nombreServicio){
		return createParams(qsMinusculas.get("service")[0].toString(), 
				qsMinusculas.get("request")[0], 
				request.getQueryString(),
				nombreServicio);
	}
	
	private GisParams transaction(HttpRequest request, Map<String, String[]> qsMinusculas, String nombreServicio){
		return createParams(qsMinusculas.get("service")[0].toString(), 
				qsMinusculas.get("request")[0], 
				request.getQueryString(),
				nombreServicio);
	}
	
	private GisParams lockFeature(HttpRequest request, Map<String, String[]> qsMinusculas, String nombreServicio){
		return createParams(qsMinusculas.get("service")[0].toString(), 
				qsMinusculas.get("request")[0], 
				request.getQueryString(),
				nombreServicio);
	}
}
