package edu.pge_gis.pge.sts.server;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.pge_gis.pge.sts.common.RequestSecurityToken;
import edu.pge_gis.pge.sts.server.exceptions.InvalidRequestException;
import edu.pge_gis.pge.sts.server.util.TrustChainContainer;
import edu.pge_gis.pge.sts.server.util.TrustChainFilter;
import edu.pge_gis.pge.sts.util.exception.ConfigurationException;
import uy.gub.agesic.beans.StoreBean;

public class STSConfiguration {

    private static STSConfiguration instance;
    private static final Logger log = LoggerFactory.getLogger(STSConfiguration.class);
    private StoreBean signingKeystoreInformation;
    private String STSName;
    private int TokenTimeout;
    private List<TrustChain> trustChainList;
    
    private static final String lock = "lock" ;

    /* 
     *  STSConfiguration es singleton
     * 
     */
    private STSConfiguration() throws ConfigurationException {
        readConfigFile();
    }
    

    public static STSConfiguration getInstance() throws ConfigurationException {
        synchronized (lock) {
            if (instance == null) {
                instance = new STSConfiguration();
            }
        }
        return instance;

    }
    
    // --------- fin singleton


    public WSTrustRequestHandler getRequestHandler() {
        WSTrustRequestHandler handler = new StandardRequestHandler();
        handler.initialize(this);
        return handler;
    }


    private synchronized void readConfigFile() throws ConfigurationException {

        try {
/*
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            InputStream is = cl.getResourceAsStream("mysts-config.xml");

            Document doc = DocumentUtil.getDocument(is);

            doc.getDocumentElement().normalize();

            log.info("Reading STS config. Root element:" + doc.getDocumentElement().getNodeName());


            // leo atributos de raiz
            Element root = doc.getDocumentElement();

            String tt = root.getAttribute("TokenTimeout");
            if (tt == null || tt.length() == 0) {
                this.TokenTimeout = 7200;
            } else {
                this.TokenTimeout = new Integer(tt);
            }


            tt = root.getAttribute("STSName");

            if (tt == null || tt.length() == 0) {
                throw new ConfigurationException("MYSTS: Error falta parametro requerido STSName");
            } else {
                this.STSName = tt;
            }

            // leo keystore info
            NodeList nList = doc.getElementsByTagName("SigningKeyStore");

            // capaz que en algun caso no precisa signing key store....
            if (nList.getLength() != 1) {
            }// problem


            Element elemKS = (Element) nList.item(0);

            String kspass = getTagValue("KeyStorePass", elemKS);
            String ksfilepath = getTagValue("KeyStoreFilePath", elemKS);
            String ksalias = getTagValue("SigningKeyAlias", elemKS);

            if (kspass == null || ksfilepath == null || ksalias == null) {
                throw new ConfigurationException("MYSTS: Error falta informacion requerida para el keystore");
            } else {
                this.signingKeystoreInformation = new StoreBean();
                this.signingKeystoreInformation.setStorePwd(kspass);
                this.signingKeystoreInformation.setAlias(ksalias);
                this.signingKeystoreInformation.setStoreFilePath(ksfilepath);
            } */


            //log.info("obteniendo las trust chains");
            //System.out.println("obteniendo las trust chains");
            ApplicationContext ctx = new ClassPathXmlApplicationContext("sts-config.xml");
            //System.out.println("Despues de ClassPathXmlApplicationContext(sts-config.xml);");
            TrustChainContainer tcc = ctx.getBean("chains", TrustChainContainer.class);

            if (tcc==null) {
            	//System.out.println("MYSTS: No se pudo cargar la configuracion (bean id chains)");
                throw new ConfigurationException("MYSTS: No se pudo cargar la configuracion (bean id chains)");
            }
            
            this.TokenTimeout = tcc.getTimeOut();
            this.STSName = tcc.getStsName();
            this.trustChainList = tcc.getChains();
            this.signingKeystoreInformation = tcc.getKeyStoreInfo();

            
            // chequeos de configuracion obligatoria
            if (this.STSName == null || this.signingKeystoreInformation == null ) {
            	//System.out.println("MYSTS: Error falta informacion requerida para el keystore");
                throw new ConfigurationException("MYSTS: Error falta informacion requerida para el keystore");
            }
        } catch (Exception e) {
        	//System.out.println("MYSTS: Error al cargar configuracion : " + e.getMessage());
            log.error("error", e);
            throw new ConfigurationException("MYSTS: Error al cargar configuracion", e);
        }


    }

    // metodo para determinar trust chain a partir de appliesto/tokentype/issuer
    public TrustChain getTrustChain(RequestSecurityToken rst) throws InvalidRequestException {


        //log.info("parametros de busqueda rst:  {}", rst);

        TrustChain tc = TrustChainFilter.getTc(trustChainList, rst.getAppliesToAddress(), rst.getTokenType(), rst.getIssuerAddress());
        if (tc == null) {
            log.error("No se encontro trustChain que matche con el RST");
            throw new InvalidRequestException("No se encontro trustChain que matche con el RST");
        }
        //log.info("cadena seleccionada: {}", tc);
        return tc;
    }
    
    //TODO esto iria en un documentutil
    // dado un elemento con hijos (element) que tienen un (text) value
    // retorna valor del elemento (hijo) con nombre stag
    private static String getTagValue(String sTag, Element eElement) {

        NodeList nlList = eElement.getElementsByTagName(sTag);
        if (nlList.getLength() == 0) {
            return null;
        }
        NodeList nlList1 = nlList.item(0).getChildNodes();
        if (nlList1.getLength() == 0) {
            return null;
        }
        Node nValue = (Node) nlList1.item(0);

        return nValue.getNodeValue();
    }

    public void setTokenTimeout(int tokenTimeout) {
        TokenTimeout = tokenTimeout;
    }

    public int getTokenTimeout() {
        return TokenTimeout;
    }

    public String getSTSName() {
        return this.STSName;

    }

    public StoreBean getSigningKeystoreInformation() {
        return signingKeystoreInformation;
    }

    public void setSigningKeystoreInformation(StoreBean signingKeystoreInformation) {
        this.signingKeystoreInformation = signingKeystoreInformation;
    }


}
