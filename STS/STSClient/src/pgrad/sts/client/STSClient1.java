package pgrad.sts.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.xml.namespace.QName;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import pgrad.sts.server.util.Util;
import pgrad.sts.common.MySTSConstants;
import pgrad.sts.common.RequestSecurityToken;

import pgrad.sts.server.exceptions.ConfigurationException;
import pgrad.sts.server.exceptions.ParsingException;
import pgrad.sts.server.exceptions.ProcessingException;
import pgrad.sts.server.exceptions.WSTrustClientException;
import pgrad.sts.server.util.DocumentUtil;
import pgrad.sts.client.RSTBean;

public class STSClient1 {

	private static Logger logger = Logger.getLogger(STSClient1.class);
	private static boolean debug = logger.isDebugEnabled();

	private String stsURL = null;

	public String getStsURL() {
		return stsURL;
	}

	public void setStsURL(String stsURL) {
		this.stsURL = stsURL;
	}

	// en realidad supongo que se retorna una assertion
	public Element issueToken(RSTBean rstBean) throws ConfigurationException, WSTrustClientException {

		if (debug) logger.debug("STSClient1 - issueToken - ini");

		// crear RST
		String RSTstr = createRST(rstBean);
		String RSTSOAPMessage = createRSTSOAPMessage(RSTstr);

		// invocar al STS, obtener RSTR
		String RSTResponseMessage = invokeSTS(RSTSOAPMessage);

		if (debug) logger.debug("RSTResponseMessage=" + RSTResponseMessage);

		Element assertion = null;

		try {
			Document rstrDocument = DocumentUtil.getDocument(RSTResponseMessage);

			//TODO interpretar soapenv, ver si es fault...
			//Element root = rstrDocument.getDocumentElement();
			
			
			
			// 1. busco <RequestSecurityTokenResponse xmlns='http://schemas.xmlsoap.org/ws/2005/02/trust'>
			Element rstr = Util.findElement(rstrDocument.getDocumentElement(), new QName(
					MySTSConstants.WS_TRUST_NS_URI, MySTSConstants.WS_TRUST_RSTR_TAG));

			if (rstr==null) 
				throw new WSTrustClientException("Error al procesar respuesta del STS. Recibido = "+DocumentUtil.asString(rstrDocument));
			
			// 2. busco <RequestedSecurityToken>
			Element requestedST = Util.findElement(rstr, new QName(MySTSConstants.WS_TRUST_NS_URI,"RequestedSecurityToken"));
			if (requestedST==null) 
				throw new WSTrustClientException("Error al procesar respuesta del STS. Recibido = "+DocumentUtil.getDOMElementAsString(rstr));

			// 3. busco assertion
//			assertion = Util.findElement(requestedST, new QName(MySTSConstants.SAML_10_ASSERTION_NS, "Assertion"));
			NodeList nl=requestedST.getChildNodes();
			if (nl.getLength() != 1) {
				throw new WSTrustClientException("Error al procesar respuesta del STS. Recibido = "+DocumentUtil.getDOMElementAsString(rstr));
			}
			assertion=(Element)nl.item(0);

//			if (assertion == null)
//				throw new WSTrustClientException("Error al procesar respuesta del STS. Recibido = "+DocumentUtil.getDOMElementAsString(rstr));

		} catch (ParsingException e) {
			e.printStackTrace();
			throw new WSTrustClientException("Error al procesar respuesta del STS");
		} catch (ProcessingException e) {
			e.printStackTrace();
			throw new WSTrustClientException("Error al procesar respuesta del STS");
		}

		return assertion;

	}

	// TODO saaj....
	private String createRSTSOAPMessage(String RSTstr) {
		String soapMessage = "<s:Envelope xmlns:a=\"http://www.w3.org/2005/08/addressing\" xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+ "<s:Header><a:Action s:mustUnderstand=\"1\">http://schemas.xmlsoap.org/ws/2005/02/trust/Issue</a:Action>"
				+ "</s:Header>" + "<s:Body>" + RSTstr + "</s:Body></s:Envelope>";
		return soapMessage;
	}

	private String invokeSTS(String RSTMessage) throws ConfigurationException, WSTrustClientException {

		String SOAPUrl = this.stsURL;
		if (this.stsURL == null)
			throw new ConfigurationException("no se ha configurado la URL del STS");

		String RSTResponseMessage = null;

		try {
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpPost httpMethod = new HttpPost(SOAPUrl);

			StringEntity requestEntity = new StringEntity(RSTMessage);
			requestEntity.setContentType("text/xml; charset=utf-8");
			httpMethod.setEntity(requestEntity);

			HttpResponse httpResponse = httpclient.execute(httpMethod);
			HttpEntity entity = httpResponse.getEntity();

			StatusLine statusLine = httpResponse.getStatusLine();
			if (debug) logger.debug("STS response: " + statusLine);

			InputStream stream = entity.getContent();
			// Get the requestSecurityTokenResponse message
			InputStreamReader isr = new InputStreamReader(stream);
			BufferedReader in = new BufferedReader(isr);

			String inputLine;
			StringBuffer stringBuffer = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				stringBuffer.append(inputLine);
			}
			in.close();

			RSTResponseMessage = stringBuffer.toString();
			if (debug) logger.debug("invokeSTS RSTR = " + RSTResponseMessage);

			int result = statusLine.getStatusCode();
			System.out.println("statuscode is " + result);
			if (result != org.apache.http.HttpStatus.SC_OK
					&& result != org.apache.http.HttpStatus.SC_ACCEPTED) {
				throw new WSTrustClientException("problema en invokeSTS " + RSTResponseMessage);
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new WSTrustClientException("Error al invocar al STS");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new WSTrustClientException("Error al invocar al STS");
		} catch (IOException e) {
			e.printStackTrace();
			throw new WSTrustClientException("Error al invocar al STS");
		} catch (Exception e) {
			e.printStackTrace();
			throw new WSTrustClientException("Error al invocar al STS");
		}

		return RSTResponseMessage;
	}

	@Deprecated
	public String createRST(RSTBean rstBean) {

		// input:
		// String policyName="urn:nac";
		// String role = "Doctor";
		// String userName="Juan";
		// String service="http://elservicio";

		// objeto RST

		RequestSecurityToken rst = new RequestSecurityToken();
		rst.setAppliesToAddress(rstBean.getService());
		rst.setRequestType("Issue");
		rst.setTokenType(MySTSConstants.TOKENTYPE_SAML11);
		rst.setSecondaryParametersRol(rstBean.getRole());
		rst.setSecondaryParametersUser(rstBean.getUserName());
//		rst.setPolicyName(rstBean.getPolicyName());
		rst.setIssuerAddress(rstBean.getPolicyName());
		rst.setBase(rstBean.getBase());

		Document rstdom = null;
		try {
			rstdom = rst.getDOM();
		} catch (ProcessingException e) {
			e.printStackTrace();
		}

		return DocumentUtil.asString(rstdom);
	}

	public Element requestSecurityToken(RequestSecurityToken rst )throws ConfigurationException, WSTrustClientException {


		if (debug) logger.debug("STSClient1 - requestST - ini");
		
		
		//por defecto el rst es para un token saml y el pedido es issue
		if (rst.getRequestType()==null){
			rst.setRequestType("Issue");
		}
		
		if (rst.getTokenType()==null){
			rst.setTokenType(MySTSConstants.TOKENTYPE_SAML11);
		}
/*
		//PROVISORIO - CARGO SECONDARY PARAMETERS CON LO DE BASE
		if (rst.getSecondaryParametersRol()==null){
			//en este caso lo tomo del base
			setSecondaryParametrsRolFromBase(rst);
		}
	*/	
		Document rstdom = null;
		try {
			rstdom = rst.getDOM();
		} catch (ProcessingException e) {
			e.printStackTrace();
		}

		String RSTstr =  DocumentUtil.asString(rstdom);
		String RSTSOAPMessage = createRSTSOAPMessage(RSTstr);

		// invocar al STS, obtener RSTR
		String RSTResponseMessage = invokeSTS(RSTSOAPMessage);

		if (debug) logger.debug("RSTResponseMessage=" + RSTResponseMessage);

		Element assertion = null;

		try {
			Document rstrDocument = DocumentUtil.getDocument(RSTResponseMessage);

			//TODO interpretar soapenv, ver si es fault...
			//Element root = rstrDocument.getDocumentElement();
			
			
			// 1. busco <RequestSecurityTokenResponse xmlns='http://schemas.xmlsoap.org/ws/2005/02/trust'>
			Element rstr = Util.findElement(rstrDocument.getDocumentElement(), new QName(
					MySTSConstants.WS_TRUST_NS_URI, MySTSConstants.WS_TRUST_RSTR_TAG));

			if (rstr==null) 
				throw new WSTrustClientException("Error al procesar respuesta del STS. Recibido = "+DocumentUtil.asString(rstrDocument));
			
			// 2. busco <RequestedSecurityToken>
			Element requestedST = Util.findElement(rstr, new QName(MySTSConstants.WS_TRUST_NS_URI,"RequestedSecurityToken"));
			if (requestedST==null) 
				throw new WSTrustClientException("Error al procesar respuesta del STS. Recibido = "+DocumentUtil.getDOMElementAsString(rstr));

			// 3. busco assertion
//			assertion = Util.findElement(requestedST, new QName(MySTSConstants.SAML_10_ASSERTION_NS, "Assertion"));
			NodeList nl=requestedST.getChildNodes();
			if (nl.getLength() != 1) {
				throw new WSTrustClientException("Error al procesar respuesta del STS. Recibido = "+DocumentUtil.getDOMElementAsString(rstr));
			}
			assertion=(Element)nl.item(0);

//			if (assertion == null)
//				throw new WSTrustClientException("Error al procesar respuesta del STS. Recibido = "+DocumentUtil.getDOMElementAsString(rstr));

		} catch (ParsingException e) {
			e.printStackTrace();
			throw new WSTrustClientException("Error al procesar respuesta del STS");
		} catch (ProcessingException e) {
			e.printStackTrace();
			throw new WSTrustClientException("Error al procesar respuesta del STS");
		}

		return assertion;
		
	}
/*
	private void setSecondaryParametrsRolFromBase(RequestSecurityToken rst) {
		
		Element base=rst.getBase();
		
		
		Element subject = Util.findElement(base,new QName(MySTSConstants.SAML_10_ASSERTION_NS,"Subject"));
		if (subject==null) return;
		
		Element nameID = Util.findElement(subject,new QName(MySTSConstants.SAML_10_ASSERTION_NS,"NameIdentifier"));
		
		if (nameID != null){
			rst.setSecondaryParametersRol(nameID.getTextContent());
		}
		
		Element attributeStatement =  Util.findElement(base,
				new QName(MySTSConstants.SAML_10_ASSERTION_NS,"AttributeStatement"));
		if (attributeStatement==null) return; 
		
		Element attribute = Util.findElement(attributeStatement,
				new QName(MySTSConstants.SAML_10_ASSERTION_NS,"Attribute"));
		if (attribute==null) return;
		//TODO chequear que sea el atributo "User"
		
		Element attributeValue = Util.findElement(attribute,
				new QName(MySTSConstants.SAML_10_ASSERTION_NS,"AttributeValue"));
		
		if (attributeValue != null){
			rst.setSecondaryParametersUser(attributeValue.getTextContent());
		}
		System.out.println("Rst obtenido: "+rst.toString());
		
		
	}
	
*/	
	
}
