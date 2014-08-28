package edu.pge_gis.pge.sts.server;

public interface TokenValidator {

    public boolean isValidToken(WSTrustRequestContext c);
}
