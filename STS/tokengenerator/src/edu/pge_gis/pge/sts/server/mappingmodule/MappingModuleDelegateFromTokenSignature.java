/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pge_gis.pge.sts.server.mappingmodule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import edu.pge_gis.pge.sts.server.STSMappingModule;
import edu.pge_gis.pge.sts.server.WSTrustRequestContext;
import edu.pge_gis.pge.sts.server.exceptions.MappingModuleException;
import edu.pge_gis.pge.sts.server.util.CheckSignature;
import edu.pge_gis.pge.sts.server.util.SignState;

public class MappingModuleDelegateFromTokenSignature implements STSMappingModule {

    private static final Logger log = LoggerFactory.getLogger(MappingModuleDelegateFromTokenSignature.class);

    @Override
    public void process(WSTrustRequestContext r) throws MappingModuleException {

        log.info("MappingModuleDelegateFromTokenSignature");

        Element base = r.getRst().getBase();

        String expression = "//ds:Signature[1]";

        SignState checkSgin = CheckSignature.check(base, expression, false);

        log.debug("checkSgin: {}", checkSgin);

        r.setDelegate(checkSgin.getPrincipal().getName());

    }
}
