package edu.pge_gis.pge.sts.server.util;

import java.util.LinkedList;
import java.util.List;

import edu.pge_gis.pge.sts.server.TrustChain;
import uy.gub.agesic.beans.StoreBean;

/**
 * 
 * esta clase solo se usa desde spring
 */
public class TrustChainContainer {

    private String stsName ;
    private Integer timeOut ;  // en segundos
    private List<TrustChain> chains ;
    private StoreBean keyStoreInfo ;
    
    
    public TrustChainContainer() {
        chains = new LinkedList<TrustChain>();
        stsName = "MY-STS";
        timeOut = 900 ; 
    }

    public List<TrustChain> getChains() {
        return chains;
    }

    public void setChains(List<TrustChain> chains) {
        this.chains = chains;
    }

    public String getStsName() {
        return stsName;
    }

    public void setStsName(String stsName) {
        this.stsName = stsName;
    }

    public Integer getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }

    public StoreBean getKeyStoreInfo() {
        return keyStoreInfo;
    }

    public void setKeyStoreInfo(StoreBean keyStoreInfo) {
        this.keyStoreInfo = keyStoreInfo;
    }
    
}
