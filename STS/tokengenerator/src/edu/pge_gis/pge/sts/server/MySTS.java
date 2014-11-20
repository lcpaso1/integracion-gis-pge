package edu.pge_gis.pge.sts.server;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import edu.pge_gis.pge.sts.common.RequestSecurityToken;
import edu.pge_gis.pge.sts.common.RequestSecurityTokenResponse;
import edu.pge_gis.pge.sts.server.exceptions.AuthenticationFailedException;
import edu.pge_gis.pge.sts.server.exceptions.InvalidRequestException;
import edu.pge_gis.pge.sts.server.exceptions.RequestFailedException;
import edu.pge_gis.pge.sts.util.DocumentUtil;
import edu.pge_gis.pge.sts.util.TransformerUtil;
import edu.pge_gis.pge.sts.util.exception.ConfigurationException;
import edu.pge_gis.pge.sts.util.exception.ProcessingException;

/*https://community.jboss.org/wiki/PicketLinkSecurityTokenService
 * 
 A client sends a security token request to PicketLinkSTS.
 PicketLinkSTS parses the request message, generating a JAXB object model.
 PicketLinkSTS reads the configuration file and creates the STSConfiguration object, if needed. Then it obtains a reference to the WSTrustRequestHandler from the configuration and delegates the request processing to the handler instance.
 The request handler uses the STSConfiguration to set default values when needed (for example, when the request doesn't specify a token lifetime value).
 The WSTrustRequestHandler creates the WSTrustRequestContext, setting the JAXB request object and the caller principal it received from PicketLinkSTS.
 The WSTrustRequestHandler uses the STSConfiguration to get the SecurityTokenProvider that must be used to process the request based on the type of the token that is being requested. Then it invokes the provider, passing the constructed WSTrustRequestContext as a parameter.
 The SecurityTokenProvider instance process the token request and stores the issued token in the request context.
 The WSTrustRequestHandler obtains the token from the context, encrypts it if needed, and constructs the WS-Trust response object containing the security token.
 PicketLinkSTS marshalls the response generated by the request handler and returns it to the client.
 */
public class MySTS {

    private static final Logger log = LoggerFactory.getLogger(MySTS.class);

    public Source invoke(Source request, WSTrustRequestContext context) throws AuthenticationFailedException, RequestFailedException, InvalidRequestException {

        // PicketLinkSTS parses the request message, generating a JAXB object model.
        // TODO te debo el jaxb
        RequestSecurityToken rst = new RequestSecurityToken();
        // rst.setFromDOM((Document) ((DOMSource) request).getNode());//no funca
        // org.jboss.ws.core.soap.SOAPBodyElementMessage cannot be cast to org.w3c.dom.Document

        try {
            Transformer transformer = TransformerUtil.getTransformer();
            DOMResult result = new DOMResult();
            transformer.transform(request, result);

            rst.setFromDOM((Document) result.getNode());

        } catch (ConfigurationException e) {
            log.error("error ", e);
            throw new RequestFailedException();
        } catch (TransformerException e) {
            log.error("error transformer", e);
            throw new RequestFailedException();
        }

        // agregado (no estaba en picketlink)
        // autenticar al solicitante?
        // revisar si esta el rol en el directorio
//		Boolean rolValido = true; // TODO
//		if (rolValido == false) {
//			throw new AuthenticationFailedException();
//		}
        // idea: authorization checker
        // hace un chequeo de autorizacion considerando el rst, 
        // decide si se puede dar el token o no
        // en el caso del organismo no pide nada....
        // en el caso de pge pide que exista rol y token del org
        // tambien podria pasar qeu el token provider vea algo de eso, por ej. qeu este el token org
        // por ahora hacer  un  checkAuthorization qeu solo mira que el rol exista 
        // y lo de token/base el el rst lo chequea el samlprovider de pge

        // por otra parte
        // en realidad la autenticacion la chequearia previamente otro modulo, 
        // por ej. un hanlder con wssecurity que extraiga username token
        // y luego se valide contra algun user backend
        // luego se copiaria el user en un contexto que podria leer aca...

        // por lo anterior me parece que es algo mas del token provider saml en particular
        // y no del sts en general...

        Document rstrDocument = null;

        try {
        	System.out.println("Antes de hacer STSConfiguration.getInstance();");
            STSConfiguration stsconfig = STSConfiguration.getInstance();
        	System.out.println("Antes de hacer stsconfig.getRequestHandler();");
            WSTrustRequestHandler handler = stsconfig.getRequestHandler();
            System.out.println("despues de stsconfig.getRequestHandler();");
            
            // podria tirar requestfailedexception o invalidrequestexception
            context.setRst(rst);

            TrustChain tc = stsconfig.getTrustChain(rst);

            System.out.println("Despues de trustchain");
            
            if (tc == null) {
                throw new RequestFailedException("No se pudo determinar una TrustChain para procesar el request");
            }

            RequestSecurityTokenResponse rstResponse = handler.issue(context, tc);

            System.out.println("despues de hacer handler.issue");
            
            // PicketLinkSTS marshalls the response generated by the request
            // handler  and returns it to the client.
            rstrDocument = rstResponse.getDOM();
            System.out.println("Despues de hacer rstResponse.getDOM();");
            
            if (log.isDebugEnabled()) {
                log.debug("El xml del rstr es: {}", DocumentUtil.asString(rstrDocument));
            }
            
//            rstrDocument = DocumentUtil.normalizeNamespaces(rstrDocument);

        } catch (ConfigurationException e) {
            log.error("Error al leer la configuracion del STS", e);
            throw new RequestFailedException("MYSTS: Error al leer la configuracion del STS");
        }

        return new DOMSource(rstrDocument);
    }

}
