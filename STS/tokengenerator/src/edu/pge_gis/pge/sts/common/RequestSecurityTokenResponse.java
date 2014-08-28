package edu.pge_gis.pge.sts.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import edu.pge_gis.pge.sts.util.DocumentUtil;
import edu.pge_gis.pge.sts.util.exception.ProcessingException;

public class RequestSecurityTokenResponse {

    //tokentype
    private String TokenType;
    //lifetime-created
    private String LifetimeCreated;
    //lifetime-expired
    private String LifetimeExpired;
    private int LifetimeSeconds;
    //requested token
    private Element RequestedToken;
    //keysize
    //keytype
    //RequestedAttachedReference
    private Document rstrDocument;

    public String getTokenType() {
        return TokenType;
    }

    public void setTokenType(String tokenType) {
        TokenType = tokenType;
    }

    public String getLifetimeCreated() {
        return LifetimeCreated;
    }

    public void setLifetimeCreated(String lifetimeCreated) {
        LifetimeCreated = lifetimeCreated;
    }

    public String getLifetimeExpired() {
        return LifetimeExpired;
    }

    public void setLifetimeExpired(String lifetimeExpired) {
        LifetimeExpired = lifetimeExpired;
    }

    public Element getRequestedToken() {
        return RequestedToken;
    }

    public void setRequestedToken(Element requestedToken) {
        RequestedToken = requestedToken;
    }

    public Document getRstrDocument() {
        return rstrDocument;
    }

    public void setRstrDocument(Document rstrDocument) {
        this.rstrDocument = rstrDocument;
    }

    public void setLifetimeOfSeconds(int seconds) {
        this.LifetimeSeconds = seconds;
    }

    public Document getDOM() {

        try {
            this.rstrDocument = DocumentUtil.createDocumentWithBaseNamespace(MySTSConstants.WS_TRUST_NS_URI, MySTSConstants.WS_TRUST_RSTR_TAG);
        } catch (ProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        addTokenType();
        addLifeTime();
        addRequestedToken();

        return this.rstrDocument;

    }

    private void addTokenType() {

        Element root = this.rstrDocument.getDocumentElement();
        Element elem = this.rstrDocument.createElement("TokenType");
        Node node = this.rstrDocument.createTextNode(this.TokenType);
//        Node node = this.rstrDocument.createTextNode(MySTSConstants.TOKENTYPE_SAML11);
        elem.appendChild(node);
        root.appendChild(elem);

    }

    private void addLifeTime() {

        Element elem, elem2;
        Node node;
        Document xmldoc = this.rstrDocument;
        Element root = xmldoc.getDocumentElement();

        // -----------------------------------------------------------------
        // Lifetime
        // -----------------------------------------------------------------

        Calendar instant = Calendar.getInstance();
        Date dateCreated = instant.getTime();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String strDateCreated = dateformat.format(dateCreated);
        instant.add(Calendar.SECOND, this.LifetimeSeconds);
        Date dateExpires = instant.getTime();
        String strDateExpires = dateformat.format(dateExpires);

        elem = xmldoc.createElement("Lifetime");
        root.appendChild(elem);
        elem2 = xmldoc.createElementNS(MySTSConstants.NAMESPACE_WSSU, "wsu:Created");
        elem.appendChild(elem2);
        node = xmldoc.createTextNode(strDateCreated);
        elem2.appendChild(node);

        elem2 = xmldoc.createElementNS(MySTSConstants.NAMESPACE_WSSU, "wsu:Expires");
        elem.appendChild(elem2);
        node = xmldoc.createTextNode(strDateExpires);
        elem2.appendChild(node);

    }

    private void addRequestedToken() {
        Element elem = this.rstrDocument.createElement("RequestedSecurityToken");
        Element root = this.rstrDocument.getDocumentElement();
        root.appendChild(elem);

        elem.appendChild(this.rstrDocument.importNode(this.RequestedToken, true));

    }
}
