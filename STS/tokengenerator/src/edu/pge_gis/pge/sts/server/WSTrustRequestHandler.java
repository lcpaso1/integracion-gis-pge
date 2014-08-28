package edu.pge_gis.pge.sts.server;

import edu.pge_gis.pge.sts.common.RequestSecurityTokenResponse;
import edu.pge_gis.pge.sts.server.exceptions.InvalidRequestException;
import edu.pge_gis.pge.sts.server.exceptions.RequestFailedException;

public interface WSTrustRequestHandler {

    public void initialize(STSConfiguration config);
    //TODO te debo el jaxb
    //public RequestSecurityTokenResponse issue(RequestSecurityToken rst);
//	public Source issue(Source rst);

    public RequestSecurityTokenResponse issue(WSTrustRequestContext context, TrustChain tc) throws RequestFailedException, InvalidRequestException;
}
