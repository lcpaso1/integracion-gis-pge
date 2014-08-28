package edu.pge_gis.pge.sts.server.api;

//import java.io.IOException;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import edu.pge_gis.pge.sts.common.RequestSecurityToken;
import edu.pge_gis.pge.sts.common.RequestSecurityTokenResponse;
import edu.pge_gis.pge.sts.server.STSConfiguration;
import edu.pge_gis.pge.sts.server.TokenAttribute;
import edu.pge_gis.pge.sts.server.TrustChain;
import edu.pge_gis.pge.sts.server.WSTrustRequestContext;
import edu.pge_gis.pge.sts.server.WSTrustRequestHandler;


public class STSServerApi {
    
    private static final Logger log = LoggerFactory.getLogger(STSServerApi.class);

    /*
    public static void main(String[] args) throws Exception {
//    public static void main(String[] args) throws ProcessingException, ConfigurationException, RequestFailedException, RequestFailedException, InvalidRequestException {
        //creo handler 
        
//        tokenGenerator ( subject, map<String, String> attributes )
        String subject= "CN=rolOrquestacionCompras, O=agesic" ;
        Map<String, String> attributes = new HashMap<String, String>();
        
        attributes.put("User", "JuanPedro");
        attributes.put("ConsumerRole", "CN=rolDoctor, O=BPS");
        attributes.put("PolicyName", "urn:intermediarioOrquestaciones");
        
        Element token1 = issueToken(subject, attributes, null, null, null);
        String domElementAsString = DocumentUtil.getDOMElementAsString(token1);
        
        System.out.printf("%s\n",domElementAsString);
        
        //prueba1();
    }*/

    public static Element issueToken(String subject, Map<String, String> attributes, String appliesTo, String tokenType, String issuer) throws Exception {
//    public static Element issueToken(String subject, Map<String, String> attributes) throws RequestFailedException, InvalidRequestException, ConfigurationException {
        

        log.debug("subject: {}", subject);
        log.debug("attributes: {}", attributes);
        
        STSConfiguration cfg = STSConfiguration.getInstance();
        WSTrustRequestHandler requestHandler = cfg.getRequestHandler();

        
        // LOAD CONTEXT ------------
        WSTrustRequestContext ctx = new WSTrustRequestContext();
        ctx.setPrincipalName(subject);
        List<TokenAttribute> tokenAttributes = ctx.getTokenAttributes();
        Set<Map.Entry<String, String>> entrySet = attributes.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            tokenAttributes.add(new TokenAttribute(entry.getKey(), null, entry.getValue()));
        }
        RequestSecurityToken rst = new RequestSecurityToken();
        rst.setAppliesToAddress(appliesTo);
        rst.setIssuerAddress(issuer);
        rst.setTokenType(tokenType);
        ctx.setRst(rst);
        ctx.setTokenAttributes(tokenAttributes);
        // FIN - LOAD CONTEXT ------------
        
        
        TrustChain tc = cfg.getTrustChain(rst);
                
        
        RequestSecurityTokenResponse rstr = requestHandler.issue(ctx, tc);
        return rstr.getRequestedToken();
    }
    
    
 
}
