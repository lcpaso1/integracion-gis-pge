package edu.pge_gis.pge.sts.server;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.pge_gis.pge.sts.common.RequestSecurityToken;
import edu.pge_gis.pge.sts.common.RequestSecurityTokenResponse;
import edu.pge_gis.pge.sts.server.exceptions.InvalidRequestException;
import edu.pge_gis.pge.sts.server.exceptions.MappingModuleException;
import edu.pge_gis.pge.sts.server.exceptions.RequestFailedException;
import uy.gub.agesic.beans.StoreBean;

public class StandardRequestHandler implements WSTrustRequestHandler {

    private static final Logger log = LoggerFactory.getLogger(StandardRequestHandler.class);
    private STSConfiguration stsconfig;

    @Override
    public void initialize(STSConfiguration config) {
        this.stsconfig = config;
    }

    @Override
    public RequestSecurityTokenResponse issue(WSTrustRequestContext context, TrustChain tc) throws RequestFailedException, InvalidRequestException {

        RequestSecurityToken rst = context.getRst();

        //log.info("WSTrustRequestHandler RST=" + rst);

        // ------------------- KEY STORE -------------------------
        StoreBean keyStoreInfoTC = tc.getKeyStoreInfo();
        if (keyStoreInfoTC != null) {
            context.setSigningKeystoreInformation(keyStoreInfoTC);
        } else {
            context.setSigningKeystoreInformation(stsconfig.getSigningKeystoreInformation());
        }
        // --------------------
        
        
        // ---------------- TIME OUT 
        context.setTimeOut(tc.getTimeOut());
        

        context.setIssuer(stsconfig.getSTSName());
        String tcIssuer = tc.getRstrIssuer();
        if (tcIssuer != null && !tcIssuer.equals("")) {
            context.setIssuer(tcIssuer);
        }



        // 1. invocar modulo validate
        TokenValidator validator = tc.getTokenValidator();
        if (validator != null) {
            boolean valid = validator.isValidToken(context);
            if (!valid) {
                throw new RequestFailedException("Token not valid"); //TODO en contexto agrego motivos				
            }
        }


        // 2. invocar modulos mapping
        try {
            List<STSMappingModule> listMapping = tc.getMappinglist();
            if (listMapping != null) {
                for (STSMappingModule mappingIter : listMapping) {

                    mappingIter.process(context);

                }
            }
        } catch (MappingModuleException e) {
            System.err.println(e.getMessage());
            log.error("Error en Mapping Module {}", e);
            throw new RequestFailedException("Error en Mapping Module"); //TODO en contexto agrego motivos
        }



        // 3. invocar issue
        SecurityTokenProvider provider = tc.getTokenProvider();
        provider.IssueToken(context);

        //controlar que tokenType de la trustChain y el tokenProvider coinciden
        String tokenTypeTc = tc.getTokenType();
        if (tokenTypeTc != null && !context.getTokenType().equals(tokenTypeTc)) {
            throw new InvalidRequestException("No coincide tokentype del contexto y la trustChain.");
        }


        //----------- OLD


        RequestSecurityTokenResponse rstResponse = new RequestSecurityTokenResponse();
        rstResponse.setLifetimeOfSeconds(60 * 60);//TODO config
        //rstResponse.setTokenType(MySTSConstants.TOKENTYPE_SAML11);  

        String tokenType = context.getTokenType();
        if (tokenType == null || tokenType.equals("")) {
            System.err.println(" ERROR en STS: tokenType==null || tokenType.equals(\"\")");
        }


        //log.info("el tokentype es: {}", context.getTokenType());
        rstResponse.setTokenType(context.getTokenType());

//        rstResponse.

        rstResponse.setRequestedToken(context.getSecurityToken());


        return rstResponse;
    }
}
