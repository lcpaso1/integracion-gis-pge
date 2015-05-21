package edu.pge_gis.pge.sts.server.mappingmodule;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.pge_gis.pge.sts.common.RequestSecurityToken;
import edu.pge_gis.pge.sts.server.STSMappingModule;
import edu.pge_gis.pge.sts.server.TokenAttribute;
import edu.pge_gis.pge.sts.server.WSTrustRequestContext;
import edu.pge_gis.pge.sts.server.exceptions.MappingModuleException;

/*
 * MM para generar leer lo que viene como Base (u OBO) 
 * cargo principal con el Subject del base
 * cargo atts que vengan en el base
 * 
 */
public class MappingModulePolicyName implements STSMappingModule {
    
    private static final Logger log = LoggerFactory.getLogger(MappingModulePolicyName.class);

    @Override
    public void process(WSTrustRequestContext ctx) throws MappingModuleException {
        
        //log.info("MappingModulePolicyName");
        
        List<TokenAttribute> tokenAttributes = ctx.getTokenAttributes();
        RequestSecurityToken rst = ctx.getRst();

        for (TokenAttribute tatt : tokenAttributes) {
            if (tatt.getName().equals("User")) {
                //log.info("se modifico el issuer address");
                tatt.setNameSpace(rst.getIssuerAddress());
            }
        }
        
    }
}
