package edu.pge_gis.ctp.rc;

import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.opensaml.DefaultBootstrap;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.Configuration;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallerFactory;
import org.opensaml.xml.io.UnmarshallingException;
import org.w3c.dom.Element;

import edu.pge_gis.ctp.rc.errors.CTPServiceException;
import pgrad.sts.client.RSTBean;
import pgrad.sts.client.STSClient1;
import pgrad.sts.server.exceptions.ConfigurationException;
import pgrad.sts.server.exceptions.ProcessingException;
import pgrad.sts.server.exceptions.WSTrustClientException;
import pgrad.sts.server.util.DocumentUtil;
import uy.gub.agesic.beans.SAMLAssertion;
import uy.gub.agesic.beans.StoreBean;
import uy.gub.agesic.exceptions.RequestSecurityTokenException;
import uy.gub.agesic.sts.client.PGEClient;


public class SecurityAggregator implements ActionPipelineProcessor {

	private static final boolean USAR_CLIENTE_JAVA = false;
	
	/** OJOTA!!!!  todos los procesadores necesitan este constructor, pero hay que agregarlo a mano*/
	public SecurityAggregator(ConfigTree config){
		
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
		// Este toma los parametros para ver que usuario usa para pedir el token de seguridad
		
		return USAR_CLIENTE_JAVA ? processUsingClienteJava(msg) : processUsingSTSClient(msg);
	}

	
	public Message processUsingSTSClient(Message msg) throws ActionProcessingException {
		// Este toma los parametros para ver que usuario usa para pedir el token de seguridad
		
		SAMLAssertion sas = new SAMLAssertion();
		sas.setAssertion(null);
	
		STSClient1 cli = new STSClient1();
		// TODO: No hardcodear url.
		cli.setStsURL("http://localhost:8080/STSServer/STSServerServlet");
		RSTBean rstBean=new RSTBean();

		rstBean.setPolicyName("urn:tokensimple");
		rstBean.setRole("CN=user0,OU=TEST_TUTORIAL,O=TEST_PE");
		rstBean.setUserName("JuanPedro");
		rstBean.setService("http://test_agesic.red.uy/Servicio");
		
		try {
			Element token = cli.issueToken(rstBean);
			
			if (token==null) {
				System.out.println("ERROR token == null");
				throw new CTPServiceException(500,"Error al obtener token de seguridad. ");
			}
			
			String token2string = DocumentUtil.getDOMElementAsString(token);
			
			// Convertir a ASSertion
			DefaultBootstrap.bootstrap();

			UnmarshallerFactory unmarshallerFactory = Configuration.getUnmarshallerFactory();
			Unmarshaller unmarshaller = unmarshallerFactory.getUnmarshaller(token);
			
			Assertion ass = (Assertion) unmarshaller.unmarshall(token);
			sas.setAssertion(ass);
			
			// agrego el token de seguridad al mensaje
			msg.getBody().add("security_token", sas);

			System.out.println("****** Security token is '" + token2string + "' ******" );
			
		} catch (ConfigurationException e) {
			e.printStackTrace();
			throw new CTPServiceException(500,"Error al obtener token de seguridad. ");
		} catch (WSTrustClientException e) {
			e.printStackTrace();
			throw new CTPServiceException(500,"Error al conectarse con el servidor de seguridad. ");
		} catch (ProcessingException e) {
			e.printStackTrace();
			throw new CTPServiceException(500,"Error al procesar parametros de seguridad. ");
		} catch (UnmarshallingException e) {
			e.printStackTrace();
			throw new CTPServiceException(500,"Error al procesar parametros de seguridad. ");
		} catch (org.opensaml.xml.ConfigurationException e) { 
			e.printStackTrace();
			throw new CTPServiceException(500,"Error al procesar parametros de seguridad. ");
		} catch (Exception e) {
			e.printStackTrace();
			throw new CTPServiceException(500,"Error al procesar solicitud. ");
		} 

		
		return msg;
	}


	public Message processUsingClienteJava(Message msg) throws ActionProcessingException {
		// Este toma los parametros para ver que usuario usa para pedir el token de seguridad
		
		String userName = "Juan"; 
		String role = "CN=user0,OU=TEST_TUTORIAL,O=TEST_PE"; 
		// TODO: No hardcodear url.
//		String service = "http://localhost:8080/STSServer/STSServerServlet"; 
		String service = "http://test_agesic.red.uy/Servicio";
		String policyName = "urn:tokensimple"; 
		String issuer = "BPS";  

		
		uy.gub.agesic.beans.RSTBean bean = new uy.gub.agesic.beans.RSTBean(); 
		bean.setUserName(userName); 
		bean.setRole(role); 
		bean.setService(service); 
		bean.setPolicyName(policyName); 
		bean.setIssuer(issuer);
		
		// Datos de trust store y keystore. TODO : usar properties.
		String alias = "stskey1"; 
		String keyStoreFilePath = "c:/ProyectoDeGrado/STS/keystore/keystore_sts.jks"; 
		String keyStorePwd = "changeit";    
		String trustStoreFilePath = "c:/ProyectoDeGrado/STS/keystore/keystore_sts.jks"; 
		String trustStorePwd = "changeit";   
		
		StoreBean keyStore = new StoreBean(); 
		keyStore.setAlias(alias); 
		keyStore.setStoreFilePath(keyStoreFilePath); 
		keyStore.setStorePwd(keyStorePwd);     
		StoreBean trustStore = new StoreBean(); 
		trustStore.setStoreFilePath(trustStoreFilePath); 
		trustStore.setStorePwd(trustStorePwd); 
		
		PGEClient client = new PGEClient(); 
		try {
			SAMLAssertion token = client.requestSecurityToken(bean, keyStore, keyStore, trustStore, "http://localhost:8080/STSServer/STSServerServlet"); 

			
			// agrego el token de seguridad al mensaje
				msg.getBody().add("security_token", token);
				
				System.out.println("****** Security token is '" + token.toString() + "' ******" );
		} catch (RequestSecurityTokenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
