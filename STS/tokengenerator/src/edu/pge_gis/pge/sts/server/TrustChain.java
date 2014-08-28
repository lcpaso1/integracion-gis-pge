package edu.pge_gis.pge.sts.server;

import java.util.LinkedList;
import java.util.List;
import uy.gub.agesic.beans.StoreBean;



public class TrustChain {

    private String name;
    private String appliesTo;
    private String tokenType;
    private String rstrIssuer;
    private String issuer;
    private String authType;
    private StoreBean keyStoreInfo;
    private TokenValidator tokenValidator;
    private List<STSMappingModule> mappinglist;
    private SecurityTokenProvider tokenProvider;

    private Integer timeOut ;
    private boolean signToken ;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppliesTo() {
        return appliesTo;
    }

    public void setAppliesTo(String appliesTo) {
        this.appliesTo = appliesTo;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public TokenValidator getTokenValidator() {
        return tokenValidator;
    }

    public void setTokenValidator(TokenValidator tokenValidator) {
        this.tokenValidator = tokenValidator;
    }

    public List<STSMappingModule> getMappinglist() {
        return mappinglist;
    }

    public void setMappinglist(List<STSMappingModule> mappinglist) {
        this.mappinglist = mappinglist;
    }

    public SecurityTokenProvider getTokenProvider() {
        return tokenProvider;
    }

    public void setTokenProvider(SecurityTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    public String getRstrIssuer() {
        return rstrIssuer;
    }

    public void setRstrIssuer(String rstrIssuer) {
        this.rstrIssuer = rstrIssuer;
    }

    public StoreBean getKeyStoreInfo() {
        return keyStoreInfo;
    }

    public void setKeyStoreInfo(StoreBean keyStoreInfo) {
        this.keyStoreInfo = keyStoreInfo;
    }

    public Integer getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }

    public boolean isSignToken() {
        return signToken;
    }

    public void setSignToken(boolean signToken) {
        this.signToken = signToken;
    }


    public TrustChain() {
        this.tokenProvider = null;
        this.tokenValidator = null;
        this.mappinglist = new LinkedList<STSMappingModule>();
        this.signToken = true; 
        this.timeOut = 900 ; 
    }

    @Override
    public String toString() {
        return "TrustChain{" + "name=" + name + ", appliesTo=" + appliesTo + ", tokenType=" + tokenType + ", rstrIssuer=" + rstrIssuer + ", issuer=" + issuer + ", authType=" + authType + '}';
    }
    
    
}
