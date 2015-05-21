package edu.pge_gis.pge.sts.server.mappingmodule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.pge_gis.pge.sts.common.RequestSecurityToken;
import edu.pge_gis.pge.sts.server.STSMappingModule;
import edu.pge_gis.pge.sts.server.TokenAttribute;
import edu.pge_gis.pge.sts.server.WSTrustRequestContext;

/*
 * MM para generar el token de organismo
 * cargo principal con el  rol,  viene en RST/SecondaryParameters/Rol, ...
 * cargo att con user que viene en RST/SecondaryParameters/User,
 * el attNS viene como issuer
 * 
 */
public class MappingModuleORG implements STSMappingModule {
    
    private static final Logger log = LoggerFactory.getLogger(MappingModuleORG.class);

    @Override
    public void process(WSTrustRequestContext r) {

        //log.info("MAPPING MODULE ORG");

        RequestSecurityToken rst = r.getRst();
        String principalName = rst.getSecondaryParametersRol();
        r.setPrincipalName(principalName);

        String attNS = rst.getIssuerAddress();
        TokenAttribute a = new TokenAttribute("User", attNS, rst.getSecondaryParametersUser());
        r.getTokenAttributes().add(a);

    }
}
