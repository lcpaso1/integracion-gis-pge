package edu.pge_gis.pge.sts.common;

import javax.xml.namespace.QName;

public interface MySTSConstants {

    //public static final String NAMESPACE_TRUST = "http://schemas.xmlsoap.org/ws/2005/02/trust";
    public static final String NAMESPACE_WSSU = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";
    String WS_TRUST_RSTR_TAG = "RequestSecurityTokenResponse";
    String WS_TRUST_RST_TAG = "RequestSecurityToken";
    String WS_TRUST_NS_URI = "http://schemas.xmlsoap.org/ws/2005/02/trust";
    public static final QName RST_QNAME = new QName(WS_TRUST_NS_URI, "RequestSecurityToken");
    public static final QName RSTR_QNAME = new QName(WS_TRUST_NS_URI, "RequestSecurityTokenResponse");
    String TOKENTYPE_SAML11 = "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1";
    String WS_TRUST_REQUEST_TYPE_ISSUE = "http://schemas.xmlsoap.org/ws/2005/02/trust/Issue";
    String WSA_NS = "http://www.w3.org/2005/08/addressing";
    String WSA_PREFIX = "wsa";
    String WSP_NS = "http://schemas.xmlsoap.org/ws/2004/09/policy";
    String WSP_PREFIX = "wsp";
    String SAML_10_ASSERTION_NS = "urn:oasis:names:tc:SAML:1.0:assertion";
    /* pl
     // WSS namespaces values.
     String WSA_NS = "http://www.w3.org/2005/08/addressing";

     String WSP_NS = "http://schemas.xmlsoap.org/ws/2004/09/policy";

     String WSU_NS = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";

     String WSSE_NS = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";

     String WSSE11_NS = "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd";

     String XENC_NS = "http://www.w3.org/2001/04/xmlenc#";

     String DSIG_NS = "http://www.w3.org/2000/09/xmldsig#";

     String SAML2_ASSERTION_NS = "urn:oasis:names:tc:SAML:2.0:assertion";
     */
}
// Ejemplo soasec
//   /** Namespace URI laid down by the WS-Security spec */
//    public static final String WS_SECURITY_NS_URI =
//        "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
//    /** Preferred namespace prefix for ws-security */
//    public static final String WS_SECURITY_PREF_NS_PREFIX = "wsse";
//    /** Security Tag as specified by the WS-Security spec */
//    public static final String WS_SECURITY_SECURITY_TAG = "Security";  */
