package pgrad.sts.client;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import pgrad.sts.common.MySTSConstants;
import pgrad.sts.common.RequestSecurityToken;
import pgrad.sts.server.exceptions.ConfigurationException;
import pgrad.sts.server.exceptions.ProcessingException;
import pgrad.sts.server.exceptions.WSTrustClientException;
import pgrad.sts.server.util.DocumentUtil;


public class Main {

	public static void main(String[] args) throws ConfigurationException, WSTrustClientException {
		//prueba1();
		//prueba2();
		prueba3();

	}
	
	private static void prueba1() throws ConfigurationException, WSTrustClientException{
		System.out.println("STSClient1 - prueba 1 - accedo a sts org");
		
		// ejemplo creacion wst client picketlink		
//			      WSTrustClient client = new WSTrustClient("PicketLinkSTS", "PicketLinkSTSPort", 
//			              "http://localhost:8080/picketlink-sts/PicketLinkSTS", 
//			              new SecurityInfo("admin", "admin"));
				
				STSClient1 cli = new STSClient1();
				cli.setStsURL("http://localhost:8080/stsorg/STSServerServlet");

				RSTBean rstBean=new RSTBean();

				rstBean.setPolicyName("urn:tokensimple");
				rstBean.setRole("CN=user0,OU=TEST_TUTORIAL,O=TEST_PE");
				rstBean.setUserName("JuanPedro");
				rstBean.setService("http://test_agesic.red.uy/Servicio");
				//rstBean.setService("http://test_agesic.red.uy/sts");
				
				// ejemplo llamada picketlink
				//assertion = client.issueToken(SAMLUtil.SAML2_TOKEN_TYPE);
				
				Element token = cli.issueToken(rstBean);
				
				if (token==null) {
					System.out.println("ERROR token == null");
					return;
				}
				
				try {
					System.out.println("token="+DocumentUtil.getDOMElementAsString(token));
				} catch (ProcessingException e) {
					e.printStackTrace();
				}

	}
	
	private static void prueba2() throws ConfigurationException, WSTrustClientException{
		System.out.println("STSClient1 - prueba 2 - accedo a sts pge");
		
		// ejemplo creacion wst client picketlink		
//			      WSTrustClient client = new WSTrustClient("PicketLinkSTS", "PicketLinkSTSPort", 
//			              "http://localhost:8080/picketlink-sts/PicketLinkSTS", 
//			              new SecurityInfo("admin", "admin"));
				
				STSClient1 cli = new STSClient1();
				cli.setStsURL("http://localhost:8080/stspge/STSServerServlet");

				RSTBean rstBean=new RSTBean();

				rstBean.setPolicyName("urn:tokensimple");
				rstBean.setRole("CN=user1,OU=TEST_TUTORIAL,O=TEST_PE");
				rstBean.setUserName("JuanPedro");
				rstBean.setService("http://test_agesic.red.uy/Servicio");
				//rstBean.setService("http://test_agesic.red.uy/sts");
				
				
				rstBean.setBase(getBaseElement());
				
				// ejemplo llamada picketlink
				//assertion = client.issueToken(SAMLUtil.SAML2_TOKEN_TYPE);
				
				Element token = cli.issueToken(rstBean);
				
				if (token==null) {
					System.out.println("ERROR token == null");
					return;
				}
				
				try {
					System.out.println("token="+DocumentUtil.getDOMElementAsString(token));
				} catch (ProcessingException e) {
					e.printStackTrace();
				}

	}

	private static void prueba3() throws ConfigurationException, WSTrustClientException{
		System.out.println("STSClient1 - prueba 3 - accedo a sts org y con token obtenido accedo a sts pge");
		
// -----------------------------------------------------
// 1 PETICION TOKEN ORGANISMO
// -----------------------------------------------------		
		
				
				STSClient1 cli = new STSClient1();
				cli.setStsURL("http://localhost:8080/STSService/STSServerServlet");

				RSTBean rstBean=new RSTBean();

				rstBean.setPolicyName("urn:tokensimple");
				rstBean.setRole("CN=user1,OU=TEST_TUTORIAL,O=TEST_PE");
				rstBean.setUserName("JuanPedro");
				rstBean.setService("http://test_agesic.red.uy/STS");
				

				Element token = cli.issueToken(rstBean);
				
				if (token==null) {
					System.out.println("ERROR token == null");
					return;
				}
				
				try {
					System.out.println("token="+DocumentUtil.getDOMElementAsString(token));
				} catch (ProcessingException e) {
					e.printStackTrace();
					throw new RuntimeException("ERROR - return");
					
				}
				
				System.out.println("STSClient1 - prueba 3 - obtuve token ahora accedo a sts pge");
// -----------------------------------------------------
// 1 PETICION TOKEN PGE
// -----------------------------------------------------
				cli.setStsURL("http://localhost:8080/stspge/STSServerServlet");

				rstBean=new RSTBean();
				
				RequestSecurityToken rstObj = new RequestSecurityToken();

//				rstObj.setPolicyName("urn:nac");
				rstObj.setIssuerAddress("urn:nac");
				rstObj.setBase(token);
				rstObj.setAppliesToAddress("http://test_agesic.red.uy/Servicio1");
				rstObj.setTokenType(MySTSConstants.SAML_10_ASSERTION_NS);

				
//				rstBean.setPolicyName("urn:nac");
//				rstBean.setRole("CN=user1,OU=TEST_TUTORIAL,O=TEST_PE");
//				rstBean.setUserName("JuanPedro");
//				rstBean.setService("http://test_agesic.red.uy/Servicio1");
				
				rstBean.setBase(token);

//				token = cli.issueToken(rstBean);
				token = cli.requestSecurityToken(rstObj);
				
				if (token==null) {
					System.out.println("ERROR token == null");
					return;
				}
				
				try {
					System.out.println("token="+DocumentUtil.getDOMElementAsString(token));
				} catch (ProcessingException e) {
					e.printStackTrace();
				}

	}

	
	
	// genera un elemento base de prueba
	// para poder invocar al sts de pge, que requiere elemento base
	private static Element getBaseElement() {
		
		Document xmldoc=null;
		try {
			xmldoc = DocumentUtil.createDocument();
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Element root = xmldoc.createElement("Assertion");
		xmldoc.appendChild(root);
		Node node = xmldoc.createTextNode("LA assertion ");
		root.appendChild(node);
		
		return xmldoc.getDocumentElement();
		
		
	}
	
				
				

}
