package edu.pge_gis.pge.sts.server;

import edu.pge_gis.pge.sts.server.exceptions.InvalidRequestException;
import edu.pge_gis.pge.sts.server.exceptions.RequestFailedException;

public interface SecurityTokenProvider {

    public void IssueToken(WSTrustRequestContext context) throws RequestFailedException, InvalidRequestException;
}
