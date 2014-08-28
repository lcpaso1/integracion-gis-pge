package edu.pge_gis.pge.sts.server.util;

import java.security.cert.X509Certificate;
import javax.security.auth.x500.X500Principal;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.keys.KeyInfo;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.signature.XMLSignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import edu.pge_gis.pge.sts.util.SoapNameSpaces;

public class CheckSignature {
    
    private static final Logger log = LoggerFactory.getLogger(CheckSignature.class);

    static {
        org.apache.xml.security.Init.init();
    }
    
    public static SignState check(Element p_elem, String expression, boolean doVerify) {
        
        XPathFactory xpf = XPathFactory.newInstance();
        XPath xpath = xpf.newXPath();
        xpath.setNamespaceContext(new SoapNameSpaces());

        Element sigElement;
        try {
            sigElement = (Element) xpath.evaluate(expression, p_elem, XPathConstants.NODE);
            XMLSignature signature = new XMLSignature(sigElement, "");


            KeyInfo ki = signature.getKeyInfo();

            if (ki != null) {

                X509Certificate cert = signature.getKeyInfo().getX509Certificate();

                if (cert != null) {
                    X500Principal subjectX500Principal = cert.getSubjectX500Principal();
                    log.info("CERT2 " + subjectX500Principal.getName());
                    
                    boolean isValidSignature = false ;
                    if (doVerify) {
                        isValidSignature = signature.checkSignatureValue(cert) ;
                    }
                    return new SignState(isValidSignature, subjectX500Principal);
                }
            }

        } catch (XPathExpressionException ex) {
            log.error("XPathExpressionException ", ex);
        } catch (XMLSignatureException ex) {
            log.error("XMLSignatureException ", ex);
        } catch (XMLSecurityException ex) {
            log.error("XMLSecurityException ", ex);
        }
        
        return null ;
    }
}
