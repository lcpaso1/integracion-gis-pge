package edu.pge_gis.pge.sts.common;

import javax.xml.namespace.QName;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import static org.w3c.dom.Node.ELEMENT_NODE;
import org.w3c.dom.NodeList;
import edu.pge_gis.pge.sts.util.DocumentUtil;
import edu.pge_gis.pge.sts.util.exception.ProcessingException;

public class RequestSecurityToken {

    private String TokenType;
    private String RequestType;
    private String AppliesToAddress;
    private String IssuerAddress; // en issuer - address pone el policyname
    private Element Base;
    private String SecondaryParametersRol;
    private String SecondaryParametersUser;
    //private String PolicyName;
    private Document rstDocument;

    public String getTokenType() {
        return TokenType;
    }

    public void setTokenType(String tokenType) {
        TokenType = tokenType;
    }

    public String getRequestType() {
        return RequestType;
    }

    public void setRequestType(String requestType) {
        RequestType = requestType;
    }

    public String getAppliesToAddress() {
        return AppliesToAddress;
    }

    public void setAppliesToAddress(String appliesToAddress) {
        AppliesToAddress = appliesToAddress;
    }

    public String getIssuerAddress() {
        return IssuerAddress;
    }

    public void setIssuerAddress(String issuerAddress) {
        IssuerAddress = issuerAddress;
    }

    public Element getBase() {
        return Base;
    }

    public void setBase(Element base) {
        Base = base;
    }

    public String getSecondaryParametersRol() {
        return SecondaryParametersRol;
    }

    public void setSecondaryParametersRol(String secondaryParametersRol) {
        SecondaryParametersRol = secondaryParametersRol;
    }
    /*
     public void setPolicyName(String policyName) {
     this.PolicyName = policyName;
     }
     public String getPolicyName() {
     return PolicyName;
     }
     */

    public void setSecondaryParametersUser(String secondaryParametersUser) {
        SecondaryParametersUser = secondaryParametersUser;
    }

    public String getSecondaryParametersUser() {
        return SecondaryParametersUser;
    }

    public Document getDOM() throws ProcessingException {

        Document xmldoc = DocumentUtil.createDocumentWithBaseNamespace(MySTSConstants.WS_TRUST_NS_URI,
                MySTSConstants.WS_TRUST_RST_TAG);

        Element root = xmldoc.getDocumentElement();
        root.setAttribute("xmlns:" + MySTSConstants.WSA_PREFIX, MySTSConstants.WSA_NS);

        addTokenType(xmldoc);
        addRequestType(xmldoc);
        addAppliesTo(xmldoc);
        addIssuer(xmldoc);
        addBase(xmldoc);
        addSecondaryParameters(xmldoc);

        return xmldoc;

    }

    private void addTokenType(Document xmldoc) {

        Element elem;
        Node node;
        Element root = xmldoc.getDocumentElement();
        elem = xmldoc.createElement("TokenType");
        node = xmldoc.createTextNode(this.TokenType);
        elem.appendChild(node);
        root.appendChild(elem);

    }

    private void setTokenTypeFromDOM() {
        Element e = getElement(this.rstDocument, new QName(MySTSConstants.WS_TRUST_NS_URI, "TokenType"));
        if (e != null) {
            this.TokenType = e.getTextContent();
        }
    }

    private void addRequestType(Document xmldoc) {

        Element elem;
        Node node;
        Element root = xmldoc.getDocumentElement();
        elem = xmldoc.createElement("RequestType");
        node = xmldoc.createTextNode(MySTSConstants.WS_TRUST_REQUEST_TYPE_ISSUE);
        elem.appendChild(node);
        root.appendChild(elem);

    }

    private void setRequestTypeFromDOM() {
        Element e = getElement(this.rstDocument, new QName(MySTSConstants.WS_TRUST_NS_URI, "RequestType"));
        if (e != null) {
            this.RequestType = e.getTextContent();
        }
    }

    private void addAppliesTo(Document xmldoc) {

        if (this.AppliesToAddress == null) {
            return;
        }


        Element elem, elem2, elem3;
        Node node;

        Element root = xmldoc.getDocumentElement();
        elem = xmldoc.createElementNS(MySTSConstants.WSP_NS, "AppliesTo");
        root.appendChild(elem);

        elem2 = xmldoc.createElement(MySTSConstants.WSA_PREFIX + ":EndpointReference");
        elem.appendChild(elem2);

        elem3 = xmldoc.createElement(MySTSConstants.WSA_PREFIX + ":Address");
        elem2.appendChild(elem3);

        node = xmldoc.createTextNode(this.AppliesToAddress);
        elem3.appendChild(node);

    }

    private void setAppliesToFromDOM() {
        Element e = getElement(this.rstDocument, new QName(MySTSConstants.WS_TRUST_NS_URI, "AppliesTo"));
        if (e == null) {
            return;
        }
        NodeList nl = e.getElementsByTagName(MySTSConstants.WSA_PREFIX + ":EndpointReference");
        if (nl.getLength() == 0) {
            return;
        }
        nl = ((Element) nl.item(0)).getElementsByTagName(MySTSConstants.WSA_PREFIX + ":Address");
        if (nl.getLength() == 0) {
            return;
        }
        if (e != null) {
            this.AppliesToAddress = nl.item(0).getTextContent();
        }
    }

    private void addIssuer(Document xmldoc) {

        //if(this.PolicyName==null) return;
        if (this.IssuerAddress == null) {
            return;
        }

        Element elem, elem2;
        Node node;

        Element root = xmldoc.getDocumentElement();
        elem = xmldoc.createElement("Issuer");
        root.appendChild(elem);

        elem2 = xmldoc.createElement(MySTSConstants.WSA_PREFIX + ":Address");
        elem.appendChild(elem2);

        //node = xmldoc.createTextNode(this.PolicyName);
        node = xmldoc.createTextNode(this.IssuerAddress);
        elem2.appendChild(node);

    }

    /*
     private void setPolicyNameFromDOM() {
     Element e = getElement(this.rstDocument, new QName(MySTSConstants.WS_TRUST_NS_URI,"Issuer"));
     if (e == null) return;
     NodeList nl=e.getElementsByTagName(MySTSConstants.WSA_PREFIX+":Address");
     if (nl.getLength()>0) this.PolicyName = nl.item(0).getTextContent();
     }
     */
    private void setIssuerAddressFromDOM() {
        Element e = getElement(this.rstDocument, new QName(MySTSConstants.WS_TRUST_NS_URI, "Issuer"));
        if (e == null) {
            return;
        }
        NodeList nl = e.getElementsByTagName(MySTSConstants.WSA_PREFIX + ":Address");
        //if (nl.getLength()>0) this.PolicyName = nl.item(0).getTextContent();
        if (nl.getLength() > 0) {
            this.IssuerAddress = nl.item(0).getTextContent();
        }
    }

    private void addSecondaryParameters(Document xmldoc) {

        if (this.SecondaryParametersRol == null) {
            return;
        }

        Element elem, elem2;
        Node node;

        Element root = xmldoc.getDocumentElement();
        elem = xmldoc.createElement("SecondaryParameters");
        root.appendChild(elem);

        elem2 = xmldoc.createElement("Rol");
        elem.appendChild(elem2);

        node = xmldoc.createTextNode(this.SecondaryParametersRol);
        elem2.appendChild(node);

        if (this.SecondaryParametersUser == null) {
            return;
        }

        elem2 = xmldoc.createElement("User");
        elem.appendChild(elem2);

        node = xmldoc.createTextNode(this.SecondaryParametersUser);
        elem2.appendChild(node);

    }

    private void setRoleFromDOM() {
        Element e = getElement(this.rstDocument, new QName(MySTSConstants.WS_TRUST_NS_URI, "SecondaryParameters"));
        if (e == null) {
            return;
        }
        NodeList nl = e.getElementsByTagName("Rol");
        if (nl.getLength() > 0) {
            this.SecondaryParametersRol = nl.item(0).getTextContent();
        }
        nl = e.getElementsByTagName("User");
        if (nl.getLength() > 0) {
            this.SecondaryParametersUser = nl.item(0).getTextContent();
        }
    }

    private void addBase(Document xmldoc) {

        if (this.Base == null) {
            return;
        }

        Element elem;

        Element root = xmldoc.getDocumentElement();
        elem = xmldoc.createElement("Base");
        root.appendChild(elem);

        elem.appendChild(xmldoc.importNode(this.Base, true));

    }

    private void setBaseFromDOM() {
        Element e = getElement(this.rstDocument, new QName(MySTSConstants.WS_TRUST_NS_URI, "Base"));
        if (e != null) {
            NodeList nl = e.getChildNodes();
            for (int i = 0; i < nl.getLength(); i++) {
                if (nl.item(i).getNodeType() == ELEMENT_NODE) {
                    this.Base = (Element) nl.item(i);
                    break;
                }
            }
        }
        e = getElement(this.rstDocument, new QName(MySTSConstants.WS_TRUST_NS_URI, "OnBehalfOf"));
        if (e != null) {
            NodeList nl = e.getChildNodes();
            for (int i = 0; i < nl.getLength(); i++) {
                if (nl.item(i).getNodeType() == ELEMENT_NODE) {
                    this.Base = (Element) nl.item(i);
                    break;
                }
            }
        }
    }

    public static Element getElement(Document doc, QName elementQName) {
        NodeList nl = doc.getElementsByTagNameNS(elementQName.getNamespaceURI(), elementQName.getLocalPart());
        if (nl.getLength() == 0) {
            nl = doc.getElementsByTagName(elementQName.getLocalPart());
            //original pl:
            //nl = doc.getElementsByTagNameNS("*", elementQName.getLocalPart());
            if (nl.getLength() == 0) {
                nl = doc.getElementsByTagName(elementQName.getPrefix() + ":" + elementQName.getLocalPart());
            }
            if (nl.getLength() == 0) {
                return null;
            }
        }
        return (Element) nl.item(0);
    }

    public void setFromDOM(Document rstdom) {

        this.rstDocument = rstdom;

        setTokenTypeFromDOM();
        setRequestTypeFromDOM();
        //setPolicyNameFromDOM();
        setIssuerAddressFromDOM();
        setAppliesToFromDOM();
        setRoleFromDOM();
        setBaseFromDOM();

    }

    
    
//    @Override
//    public String toString() {
//        return "RequestSecurityToken [TokenType=" + TokenType + ", RequestType=" + RequestType
//                + ", AppliesToAddress=" + AppliesToAddress + ", IssuerAddress=" + IssuerAddress
//                + ", Base=" + Base + ", SecondaryParametersRol=" + SecondaryParametersRol
//                + ", SecondaryParametersUser=" + SecondaryParametersUser + "]";
//    }

    @Override
    public String toString() {
        return "RequestSecurityToken{" + "TokenType=" + TokenType + ", RequestType=" + RequestType + ", AppliesToAddress=" + AppliesToAddress + ", IssuerAddress=" + IssuerAddress + ", SecondaryParametersRol=" + SecondaryParametersRol + ", SecondaryParametersUser=" + SecondaryParametersUser + '}';
    }
}

/* EJEMPLO PGECLIENT
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