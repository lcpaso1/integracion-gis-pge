package edu.pge_gis.pge.sts.server.test;

//import java.io.IOException;

//import javax.xml.soap.SOAPException;
//
//import javax.xml.transform.Source;
//import javax.xml.transform.TransformerException;
//import javax.xml.transform.dom.DOMSource;
//
//import org.w3c.dom.Document;
//import pgrad.sts.common.RequestSecurityToken;
//import pgrad.sts.server.MySTS;
//
//import pgrad.sts.server.exceptions.AuthenticationFailedException;
//import pgrad.sts.server.exceptions.ConfigurationException;
//import pgrad.sts.server.exceptions.InvalidRequestException;
//import pgrad.sts.server.exceptions.ParsingException;
//import pgrad.sts.server.exceptions.ProcessingException;
//import pgrad.sts.server.exceptions.RequestFailedException;
//import pgrad.sts.server.util.DocumentUtil;
//import pgrad.sts.server.util.MyXMLUtils;
//import pgrad.sts.common.*;

public class STSTest {

    public static void main(String[] args) /*throws SOAPException, IOException, TransformerException, AuthenticationFailedException, RequestFailedException, InvalidRequestException */ {
        //prueba1();
    }
    /*	
     public static void prueba1() throws AuthenticationFailedException, RequestFailedException, InvalidRequestException{
     System.out.println("STS Test ini PRUEBA 1");
		
     MySTS sts = new MySTS();

     // invocar STS
     //Source RequestSource = new DOMSource(node);
     Source RequestSource = null;
     DOMSource ResponseSource = (DOMSource) sts.invoke(RequestSource);
     System.out.println("response = "+MyXMLUtils.xmlToString(ResponseSource.getNode()));
     }
	
     public static void prueba2() throws SOAPException, IOException, AuthenticationFailedException, RequestFailedException, InvalidRequestException{
     System.out.println("STS Test ini PRUEBA 2");
		
     MySTS sts = new MySTS();

     // invocar STS
     Source RequestSource = null;
     DOMSource ResponseSource = (DOMSource) sts.invoke(RequestSource);
     System.out.println("response = "+MyXMLUtils.xmlToString(ResponseSource.getNode()));
		

     }
	
     public static void prueba3() throws AuthenticationFailedException, RequestFailedException, InvalidRequestException {
		
     //input:
     String policyName="urn:nac";
     String role = "Doctor";
     String userName="Juan";
     String service="http://elservicio";
	
		
     //objeto RST
		
     RequestSecurityToken rst = new RequestSecurityToken();
     rst.setAppliesToAddress(service);
     rst.setRequestType("Issue");
     rst.setTokenType(MySTSConstants.TOKENTYPE_SAML11);
     rst.setSecondaryParametersRol(role);
     rst.setSecondaryParametersUser(userName);
     rst.setPolicyName(policyName);
     //rst.se
		
     Document ass=createAssertion();
     rst.setBase(ass.getDocumentElement());
		
     Document rstdom=null;
     try{
     rstdom = rst.getDOM();
     } catch (ProcessingException e) {
     // TODO Auto-generated catch block
     e.printStackTrace();
     }
		
     System.out.println("RST DOM =" + MyXMLUtils.xmlToString(rstdom));
		
		
     MySTS sts = new MySTS();

     // invocar STS
     Source RequestSource = new DOMSource(rstdom);
     DOMSource ResponseSource = (DOMSource) sts.invoke(RequestSource);
     System.out.println("response = "+MyXMLUtils.xmlToString(ResponseSource.getNode()));
		
		
     /* PGECLIENT
     * 	
     String soapMessagePartOne = "
     <s:Envelope xmlns:a=\"http://www.w3.org/2005/08/addressing\" xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\">"
     + "<s:Header>
     <a:Action s:mustUnderstand=\"1\">http://schemas.xmlsoap.org/ws/2005/02/trust/Issue</a:Action>"
     + "<a:MessageID>urn:uuid:"+ messageID+ "</a:MessageID>"
     + "</s:Header>
			
     <s:Body>
			
     <RequestSecurityToken xmlns=\"http://schemas.xmlsoap.org/ws/2005/02/trust\">"
     <TokenType>http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1</TokenType>"
     + "<AppliesTo xmlns=\"http://schemas.xmlsoap.org/ws/2004/09/policy\"><a:EndpointReference>"
     + "<a:Address>"
     + service
     + "</a:Address></a:EndpointReference></AppliesTo>"
			
     + "<RequestType>http://schemas.xmlsoap.org/ws/2005/02/trust/Issue</RequestType>
     <Issuer>"
     + "<a:Address>" + policyName + "</a:Address>
     </Issuer>
     <Base>" ASSERTION
     </Base>
     <SecondaryParameters><Rol>+ role+ "</Rol></SecondaryParameters>
     </RequestSecurityToken>
			
     </s:Body></s:Envelope>";
     */
    /*		
     }
	
	
     private static Document createAssertion(){
		
     Document doc=null;
		
     String docString="<Assertion ID='ID_6a025570-5ad2-4705-a4bb-238726ba99c9' IssueInstant='2012-05-06T00:41:51.017Z' Version='2.0' xmlns='urn:oasis:names:tc:SAML:2.0:assertion' xmlns:ns2='http://www.w3.org/2001/04/xmlenc#' xmlns:ns3='http://www.w3.org/2000/09/xmldsig#'>"
     +"<Issuer>PicketLinkSTS</Issuer>"
     +"<Subject><NameID NameQualifier='urn:picketlink:identity-federation'>admin</NameID>"
     +"<SubjectConfirmation Method='urn:oasis:names:tc:SAML:2.0:cm:bearer'/></Subject>"
     +"<Conditions NotBefore='2012-05-06T00:41:51.017Z' NotOnOrAfter='2012-05-06T02:41:51017Z'/>"
     +"</Assertion>";
			
     try {
     doc=DocumentUtil.getDocument(docString);
     } catch (ConfigurationException e) {
     // TODO Auto-generated catch block
     e.printStackTrace();
     } catch (ParsingException e) {
     // TODO Auto-generated catch block
     e.printStackTrace();
     } catch (ProcessingException e) {
     // TODO Auto-generated catch block
     e.printStackTrace();
     }
     return doc;
		
     }
     */
}
