package edu.pge_gis.pge.sts.server;

import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Element;

import edu.pge_gis.pge.sts.common.RequestSecurityToken;
import uy.gub.agesic.beans.StoreBean;

public class WSTrustRequestContext {

    private Element token;
    private RequestSecurityToken rst;
    private StoreBean signingKeystoreInformation;
    private String issuer;
    private String principalName;
    private String errorMessage;
    private String tokenType;
    private String delegate;
    private List<TokenAttribute> tokenAttributes;
    private int timeOut ;

    //------------------------------------------------------------
    public WSTrustRequestContext() {
        this.tokenAttributes = new LinkedList<TokenAttribute>();
    }

    public RequestSecurityToken getRst() {
        return rst;
    }

    public void setRst(RequestSecurityToken rst) {
        this.rst = rst;
    }

    public Element getSecurityToken() {
        return token;
    }

    public void setSecurityToken(Element token) {
        this.token = token;
    }

    public void setSigningKeystoreInformation(StoreBean signingKeystoreInformation) {
        this.signingKeystoreInformation = signingKeystoreInformation;
    }

    public StoreBean getSigningKeystoreInformation() {
        return signingKeystoreInformation;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setTokenAttributes(List<TokenAttribute> tokenAttributes) {
        this.tokenAttributes = tokenAttributes;
    }

    public List<TokenAttribute> getTokenAttributes() {
        return tokenAttributes;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setDelegate(String delegate) {
        this.delegate = delegate;
    }

    public String getDelegate() {
        return delegate;
    }

    public Element getToken() {
        return token;
    }

    public void setToken(Element token) {
        this.token = token;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }
    
    
}
