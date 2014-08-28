package edu.pge_gis.pge.sts.token;

import java.util.Map;

import javax.ejb.Stateless;

import org.w3c.dom.Element;

import edu.pge_gis.pge.sts.server.api.STSServerApi;

@Stateless
public class TokenGenerator implements TokenGeneratorLocal {


    @Override
    public Element issueToken(String subject, Map<String, String> attributes, String appliesTo, String tokenType, String issuer) throws Exception {
        return STSServerApi.issueToken(subject, attributes, appliesTo, tokenType, issuer);
    }
}
