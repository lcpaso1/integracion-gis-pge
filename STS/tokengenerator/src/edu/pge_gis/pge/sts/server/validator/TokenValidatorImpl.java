package edu.pge_gis.pge.sts.server.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import edu.pge_gis.pge.sts.server.TokenValidator;
import edu.pge_gis.pge.sts.server.WSTrustRequestContext;
import edu.pge_gis.pge.sts.server.util.CheckSignature;
import edu.pge_gis.pge.sts.server.util.SignState;

/**
 * En este token validator se chequea que cierre la firma del token base
 *
 * @author martin
 */
public class TokenValidatorImpl implements TokenValidator {

    private static final Logger log = LoggerFactory.getLogger(TokenValidatorImpl.class);


    @Override
    public boolean isValidToken(WSTrustRequestContext c) {

        log.info("isValidToken");
        
        Element base = c.getRst().getBase();

        String expression = "//ds:Signature[1]";
        
        SignState checkSgin = CheckSignature.check(base, expression, true);
        
        log.debug("checkSgin: {}",checkSgin);
        
        return checkSgin.isValid();
    }
}
