package edu.pge_gis.pge.sts.server.mappingmodule;

import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.pge_gis.pge.sts.common.RequestSecurityToken;
import edu.pge_gis.pge.sts.server.STSMappingModule;
import edu.pge_gis.pge.sts.server.TokenAttribute;
import edu.pge_gis.pge.sts.server.WSTrustRequestContext;
import edu.pge_gis.pge.sts.server.exceptions.MappingModuleException;
import edu.pge_gis.pge.sts.util.SoapNameSpaces;

/*
 * MM para generar leer lo que viene como Base (u OBO) 
 * cargo principal con el Subject del base
 * cargo atts que vengan en el base
 * 
 */
public class MappingModuleBase2Context implements STSMappingModule {
    
    private static final Logger log = LoggerFactory.getLogger(MappingModuleBase2Context.class);

    @Override
    public void process(WSTrustRequestContext r) throws MappingModuleException {

        log.info("MAPPING MODULE BASE 2 CONTEXT"); 

        RequestSecurityToken rst = r.getRst();
        Element base = rst.getBase();

        /*
         * ejemplo xpath XPathFactory factory = XPathFactory.newInstance();
         * XPath xpath = factory.newXPath(); xpath.setNamespaceContext(new
         * SoapNameSpaces());
         * 
         * XPathExpression expr = xpath.compile(basePath);
         * 
         * Object result = expr.evaluate(documentSoapEnv,
         * XPathConstants.NODESET); NodeList nodes = (NodeList) result;
         */

        // XPATH INIT
        XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        xpath.setNamespaceContext(new SoapNameSpaces());

        XPathExpression expr;
        String searchPath = null;
        String str1;

        try {
//            try {
//                String domElementAsString = DocumentUtil.getDOMElementAsString(base);
//                log.info("el base es: {}",domElementAsString
//                        );
//            } catch (ProcessingException ex) {
//            } catch (ConfigurationException ex) {
//            }
            

            // tomo subject del base

            searchPath = "//mins4:Assertion/mins4:AuthenticationStatement/mins4:Subject/mins4:NameIdentifier/text()";
            expr = xpath.compile(searchPath);
            str1 = (String) expr.evaluate(base, XPathConstants.STRING);
            r.setPrincipalName(str1);

//            searchPath = "//saml1:Assertion/saml1:AttributeStatement";
            searchPath = "//mins4:Assertion/mins4:AttributeStatement/mins4:Attribute";
            expr = xpath.compile(searchPath);
            NodeList nodes = (NodeList) expr.evaluate(base, XPathConstants.NODESET);

            List<TokenAttribute> attlist = r.getTokenAttributes();
            
            Node attnode = null ;
            for (int i = 1; i <= nodes.getLength(); i++) {
                if (attnode==null) {
                    attnode = nodes.item(0).getParentNode();
                }
//                try {
//                    String domElementAsString2 = DocumentUtil.getDOMElementAsString((Element) attnode);
//                    log.info("<<<<<<<<el nodeAttribute actual es: {}",domElementAsString2);
//                } catch (ProcessingException ex) {
//                } catch (ConfigurationException ex) {
//                }
                
                String searchPath1 = "//mins4:Attribute["+i+"]/@AttributeName";
                XPathExpression expr1 = xpath.compile(searchPath1);

                String searchPath2 = "//mins4:Attribute["+i+"]/@AttributeNamespace";
                XPathExpression expr2 = xpath.compile(searchPath2);

                String searchPath3 = "//mins4:Attribute["+i+"]/mins4:AttributeValue/text()";
                XPathExpression expr3 = xpath.compile(searchPath3);
                
                //----------------
                
                TokenAttribute att = new TokenAttribute();

                String str2 = (String) expr1.evaluate(attnode, XPathConstants.STRING);
                att.setName(str2);
                log.debug("<<<<<<<<guardando atrubuto de nombre: {}",str2);

                String str3 = (String) expr2.evaluate(attnode, XPathConstants.STRING);
                att.setNameSpace(str3);

                String str4 = (String) expr3.evaluate(attnode, XPathConstants.STRING);
                att.setValue(str4);
                
                log.debug("<<<<<<<<token attribute generado: {}",att);

                attlist.add(att);

            }


        } catch (XPathExpressionException e) {
            log.error("ERRor al evaluar XPATH ",e);
            throw new MappingModuleException("ERROR al evaluar XPATH = " + searchPath);
        }
        

        /*
         * 
         * String principalName = rst.getSecondaryParametersRol();
         * r.setPrincipalName(principalName);
         * 
         * String attNS = rst.getIssuerAddress(); TokenAttribute a = new
         * TokenAttribute("User", attNS, rst.getSecondaryParametersUser());
         * r.getTokenAttributes().add(a);
         */
    }
}
