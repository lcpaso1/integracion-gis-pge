package edu.pge_gis.pge.sts.token;

import java.util.Map;
import javax.ejb.Local;
import org.w3c.dom.Element;


@Local
public interface TokenGeneratorLocal {

    public Element issueToken(String subject, Map<String, String> attributes, String appliesTo, String tokenType, String issuer) throws Exception;
}
