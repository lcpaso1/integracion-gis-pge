package edu.pge_gis.pge.sts.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import edu.pge_gis.pge.sts.util.DocumentUtil;
import edu.pge_gis.pge.sts.util.SOAPUtil;
import edu.pge_gis.pge.sts.common.MySTSConstants;
import edu.pge_gis.pge.sts.server.MySTS;
import edu.pge_gis.pge.sts.server.WSTrustRequestContext;
import edu.pge_gis.pge.sts.server.exceptions.AuthenticationFailedException;
import edu.pge_gis.pge.sts.server.exceptions.InvalidRequestException;
import edu.pge_gis.pge.sts.server.exceptions.RequestFailedException;


public class STSServerServlet extends HttpServlet {


    private static final Logger log = LoggerFactory.getLogger(STSServerServlet.class);
    private static final long serialVersionUID = 1L;
    static MessageFactory messageFactory;

    static {
    	log.info("STSServerServlet::static block");
        try {
            messageFactory = MessageFactory.newInstance();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public STSServerServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter w = response.getWriter();
        w.println("mysts1 v2.0: post me please");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	log.info("STSServerServlet::doPost");
        System.out.println("STSServerServlet - doPost inicio");

        try {
            // Get all the headers from the HTTP request
            MimeHeaders headers = getHeaders(req);

            log.info("STSServerServlet INICIO doPost");


            // Construct a SOAPMessage from the XML in the request body
            InputStream is = req.getInputStream();
            SOAPMessage soapRequest = messageFactory.createMessage(headers, is);

            // Handle soapReqest ANTES
            // SOAPMessage soapResponse = soapProcessor.handleSOAPRequest(soapRequest);

            // Nuevo con pl
            SOAPMessage soapResponse = handleRequest(soapRequest, req);

            // Write to HttpServeltResponse
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setContentType("text/xml;charset=\"utf-8\"");
            OutputStream os = resp.getOutputStream();
            soapResponse.writeTo(os);
//            soapResponse.writeTo(new LogOutputStream(log, "info"));
            os.flush();
        } catch (SOAPException e) {
            throw new IOException("Exception while creating SOAP message.", e);
        }
    }

    @SuppressWarnings("unchecked")
    static MimeHeaders getHeaders(HttpServletRequest req) {
    	System.out.println("STSServerServlet::getHeaders");
        Enumeration headerNames = req.getHeaderNames();
        MimeHeaders headers = new MimeHeaders();
        while (headerNames.hasMoreElements()) {
            String headerName = (String) headerNames.nextElement();
            String headerValue = req.getHeader(headerName);
            StringTokenizer values = new StringTokenizer(headerValue, ",");
            while (values.hasMoreTokens()) {
                headers.addHeader(headerName, values.nextToken().trim());
            }
        }
        return headers;
    }

    private static SOAPMessage handleRequest(SOAPMessage soapRequest, HttpServletRequest req) throws ServletException, IOException {
//    private static Source handleRequest(SOAPMessage soapRequest, HttpServletRequest req) throws ServletException, IOException {

		System.out.println("MYSTS: HANDLE SOAP MESSAGE request=");

        SOAPMessage soapResponse = null;
        Source responseSource = null;

        try {

            SOAPBody soapBody = soapRequest.getSOAPBody();




            //Node 
            //node = soapBody.getFirstChild(); //agarra espacio

            //Busco RST dentro del Body

            Iterator iter = soapBody.getChildElements(MySTSConstants.RST_QNAME);
            if (!iter.hasNext()) {
                return SOAPUtil.createFault("MYSTS: invalid SOAP request (RST not found)");
            }

            Node node = (Node) iter.next();


            /*
             if (node instanceof SOAPElement) {
             SOAPElement soapElement = (SOAPElement) node;
             QName qname = soapElement.getElementQName();
             if (!MySTSConstants.RST_QNAME.equals(qname)) 
             return SOAPUtil.createFault("MYSTS: The request was invalid or malformed");
             }else{
             System.out.println("ERROR node NOT instanceof SOAPElement");
             return SOAPUtil.createFault("MYSTS: invalid SOAP request");
             }
             */

            MySTS sts = new MySTS();
            WSTrustRequestContext context = new WSTrustRequestContext();

            //guardo en el contexto atributos http
            saveHttpContext(req, context);

            // invocar STS
            Source requestSource = new DOMSource(node);


            try {
                responseSource = sts.invoke(requestSource, context);
            } catch (AuthenticationFailedException e) {
                return SOAPUtil.createFault("MYSTS: Authentication Failed");
                //TODO faltaria el faultcode wst:FailedAuthentication
            } catch (InvalidRequestException e) {
                return SOAPUtil.createFault("MYSTS: The request was invalid or malformed" + e.getMessage());
            } catch (RequestFailedException e) {
                return SOAPUtil.createFault("MYSTS: The specified request failed: " + e.getMessage());
            }
            
            soapResponse = convert(responseSource);

        } catch (SOAPException e) {
            log.error("Exception while handling SOAP message", e);
            throw new IOException("Exception while handling SOAP message.", e);
        }
        return soapResponse;
    }

    private static void saveHttpContext(HttpServletRequest req, WSTrustRequestContext context) {


        String remoteUser = req.getRemoteUser();
        //Principal userPrincipal = req.getUserPrincipal();
        if (remoteUser != null) {
            log.debug("REMOTE USER=" + remoteUser);
            //System.out.println("Principal name="+userPrincipal.getName());
            context.setPrincipalName(remoteUser);
            //req.getRemoteAddr();
        }

        // si fuera cert, guardo cert, etc


    }

    // PL choreao from PicketLinkSTS.java
    private static SOAPMessage convert(Source theResponse) throws ServletException {
        // Martin: aca se repiten los namespaces de saml1!!!
        try {
            SOAPMessage response = SOAPUtil.create();
            Document theResponseDoc = (Document) DocumentUtil.getNodeFromSource(theResponse);
            response.getSOAPBody().addDocument(theResponseDoc);
            return response;
        } catch (Exception e) {
            // throw new WebServiceException(e);
            throw new ServletException(e);
        }
    }  
    
}
