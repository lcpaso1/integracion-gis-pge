package edu.pge_gis.ctp.rc;

import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.w3c.dom.Element;

import pgrad.sts.client.RSTBean;
import pgrad.sts.client.STSClient1;
import pgrad.sts.server.exceptions.ConfigurationException;
import pgrad.sts.server.exceptions.ProcessingException;
import pgrad.sts.server.exceptions.WSTrustClientException;
import pgrad.sts.server.util.DocumentUtil;
import uy.gub.agesic.beans.SAMLAssertion;


public class SecurityAggregator implements ActionPipelineProcessor {

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

		// TODO: obtener el token de seguridad del STS 
		String securityToken = "&&&&&&&&&&&&&&&&_dummy_security_token_&&&&&&&&&&&&&&&&&&&";
		
		// Dummy SAMLAssertion 

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
				// TODO Lanzar excepcion relativa a STS 
			}
			
			String token2string = DocumentUtil.getDOMElementAsString(token);
			
			// agrego el token de seguridad al mensaje
			msg.getBody().add("security_token", token2string);
			
			System.out.println("****** Security token is '" + token2string + "' ******" );
			
		} catch (ConfigurationException e) {
			// TODO Lanzar excepcion relativa a STS 
			e.printStackTrace();
		} catch (WSTrustClientException e) {
			// TODO Lanzar excepcion relativa a STS 
			e.printStackTrace();
		} catch (ProcessingException e) {
			// TODO Lanzar excepcion relativa a STS 
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
