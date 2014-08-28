package edu.pge_gis.pge.sts.server;

import edu.pge_gis.pge.sts.server.exceptions.MappingModuleException;

public interface STSMappingModule {

    public void process(WSTrustRequestContext r) throws MappingModuleException;
}
