package edu.pge_gis.pge.sts.server.tokenprovider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;

import org.joda.time.DateTime;
import org.opensaml.Configuration;
import org.opensaml.DefaultBootstrap;
import org.opensaml.common.IdentifierGenerator;
import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.common.SAMLVersion;
import org.opensaml.common.impl.SecureRandomIdentifierGenerator;
import org.opensaml.saml1.core.Assertion;
import org.opensaml.saml1.core.Attribute;
import org.opensaml.saml1.core.AttributeStatement;
import org.opensaml.saml1.core.AttributeValue;
import org.opensaml.saml1.core.Audience;
import org.opensaml.saml1.core.AudienceRestrictionCondition;
import org.opensaml.saml1.core.AuthenticationStatement;
import org.opensaml.saml1.core.Conditions;
import org.opensaml.saml1.core.ConfirmationMethod;
import org.opensaml.saml1.core.NameIdentifier;
import org.opensaml.saml1.core.Subject;
import org.opensaml.saml1.core.SubjectConfirmation;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallerFactory;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.schema.XSString;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.security.keyinfo.KeyInfoHelper;
import org.opensaml.xml.security.x509.BasicX509Credential;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.signature.SignatureConstants;
import org.opensaml.xml.signature.SignatureException;
import org.opensaml.xml.signature.Signer;
import org.opensaml.xml.signature.impl.KeyInfoBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

import edu.pge_gis.pge.sts.server.SecurityTokenProvider;
import edu.pge_gis.pge.sts.server.TokenAttribute;
import edu.pge_gis.pge.sts.server.WSTrustRequestContext;
import edu.pge_gis.pge.sts.server.exceptions.InvalidRequestException;
import edu.pge_gis.pge.sts.server.exceptions.RequestFailedException;
import edu.pge_gis.pge.sts.util.exception.ProcessingException;
import uy.gub.agesic.AgesicConstants;
import uy.gub.agesic.beans.ClientCredential;
import uy.gub.agesic.beans.SAMLAssertion;
import uy.gub.agesic.beans.StoreBean;
import uy.gub.agesic.exceptions.AssertionException;

// diferencias con sts organismo:
// chequea rol LISTO
// requiere rst.base LISTO
// genera audience LISTO
public class SAMLTokenProviderBasic1 implements SecurityTokenProvider {

    private static X509Certificate x509Certificate;
    private static ClientCredential staticCredential;
    private static final Logger log = LoggerFactory.getLogger(SAMLTokenProviderBasic1.class);

    @Override
    public void IssueToken(WSTrustRequestContext context) throws RequestFailedException, InvalidRequestException {


        context.setTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1");


        // Get Client Credential // TODO sacarlo para otro lado SAMLTokenProviderHelper
        StoreBean keystoreBean = context.getSigningKeystoreInformation();

        SAMLAssertion ass = null;

        try {
            ClientCredential credential = getCredentialFromStoreBean(keystoreBean);

            ass = this.generateSignedAssertion(credential, context);

            

        } catch (AssertionException e) {
            log.error("error", e);
            throw new RequestFailedException(e);
        } catch (ProcessingException e) {
            log.error("error", e);
            throw new RequestFailedException(e);
        }

        context.setSecurityToken(ass.getDOM());
    }

    @SuppressWarnings("unchecked")
//	public SAMLAssertion generateSignedAssertion(ClientCredential signingCredential, RequestSecurityToken rst, String Issuer)
    public SAMLAssertion generateSignedAssertion(ClientCredential signingCredential, WSTrustRequestContext context) throws AssertionException {

        //log.info(" >>>>>>>>>>>>>>>>>>>>>>>>> generateSignedAssertion");

        Credential credential = signingCredential.getCredential();

        DateTime authenticationInstant = new DateTime();
        DateTime issueInstant = new DateTime();
        DateTime conditionTimeNotBefore = new DateTime().minusSeconds(context.getTimeOut());
        DateTime conditionTimeNotAfter = new DateTime().plusSeconds(context.getTimeOut());

        // SAML User Info
        String strIssuer = context.getIssuer();
//		String strRole = rst.getSecondaryParametersRol();
//		String strPolicyName = rst.getPolicyName();
//		String userName = rst.getSecondaryParametersUser();
//		String audienceStr = rst.getAppliesToAddress();

        try {

            DefaultBootstrap.bootstrap();
            XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();


            // Create assertionID
            IdentifierGenerator idGenerator = new SecureRandomIdentifierGenerator();
            String strAssertionID = idGenerator.generateIdentifier();

            // Create the assertion
            SAMLObjectBuilder assertionBuilder = (SAMLObjectBuilder) builderFactory
                    .getBuilder(Assertion.DEFAULT_ELEMENT_NAME);
            Assertion assertion = (Assertion) assertionBuilder.buildObject();
            assertion.setIssuer(strIssuer);
            assertion.setIssueInstant(issueInstant);
            assertion.setVersion(SAMLVersion.VERSION_10);


            // Create authentication statement subject
            Subject authStatementSubject = getSubjectForSignedToken(context, builderFactory);

            // Create Authentication Statement
            SAMLObjectBuilder authStatementBuilder = (SAMLObjectBuilder) builderFactory
                    .getBuilder(AuthenticationStatement.DEFAULT_ELEMENT_NAME);
            AuthenticationStatement authnStatement = (AuthenticationStatement) authStatementBuilder
                    .buildObject();
            authnStatement.setSubject(authStatementSubject);
            authnStatement.setAuthenticationMethod(AgesicConstants.SAML10_PASSWD_AUTH_METHOD);
            authnStatement.setAuthenticationInstant(authenticationInstant);


            assertion.getAuthenticationStatements().add(authnStatement);


            //-------------------------------------------------------------
            // Create the attribute statement

            AttributeStatement attrStatement = buildAttributeStatement(context, builderFactory);
            if (attrStatement != null) {
                assertion.getAttributeStatements().add(attrStatement);
            }


            // fin att
            //-------------------------------------------------------------


            SAMLObjectBuilder conditionsBuilder = (SAMLObjectBuilder) builderFactory
                    .getBuilder(Conditions.DEFAULT_ELEMENT_NAME);


            Conditions conditions = (Conditions) conditionsBuilder.buildObject();

            conditions.setNotBefore(conditionTimeNotBefore);
            conditions.setNotOnOrAfter(conditionTimeNotAfter);

            //	emilio
            // agrego el audience restriction condition, basado en el applies to del rst

            String audienceStr = context.getRst().getAppliesToAddress();

            SAMLObjectBuilder audienceB = (SAMLObjectBuilder) builderFactory.getBuilder(Audience.DEFAULT_ELEMENT_NAME);
            Audience audience = (Audience) audienceB.buildObject();
            audience.setUri(audienceStr);
            SAMLObjectBuilder audienceRB = (SAMLObjectBuilder) builderFactory.getBuilder(AudienceRestrictionCondition.DEFAULT_ELEMENT_NAME);
            AudienceRestrictionCondition audienceRestrictions = (AudienceRestrictionCondition) audienceRB.buildObject();
            audienceRestrictions.getAudiences().add(audience);

            conditions.getAudienceRestrictionConditions().add(audienceRestrictions);
//            
            // fin emilio




            //assertion.getAttributeStatements().add(attrStatement);


            assertion.setConditions(conditions);

            // Create signature
            Signature signature = (Signature) Configuration.getBuilderFactory()
                    .getBuilder(Signature.DEFAULT_ELEMENT_NAME)
                    .buildObject(Signature.DEFAULT_ELEMENT_NAME);

            signature.setSigningCredential(credential);
            signature.setSignatureAlgorithm(SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA1);
            signature.setCanonicalizationAlgorithm(SignatureConstants.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);

            KeyInfoBuilder keyInfoBuilder = (KeyInfoBuilder) builderFactory
                    .getBuilder(KeyInfo.DEFAULT_ELEMENT_NAME);
            KeyInfo keyinfo = (KeyInfo) keyInfoBuilder.buildObject(KeyInfo.DEFAULT_ELEMENT_NAME);
            KeyInfoHelper.addCertificate(keyinfo, x509Certificate);
            signature.setKeyInfo(keyinfo);

            assertion.setID(strAssertionID);
            assertion.setSignature(signature);

            // Get the marshaller factory
            MarshallerFactory marshallerFactory = Configuration.getMarshallerFactory();

            // Get the Subject marshaller and generate assertion's dom
            // representation
            Marshaller marshaller = marshallerFactory.getMarshaller(assertion);
            Element element = marshaller.marshall(assertion);

            // Sign SAML Assertion
            Signer.signObject(signature);


            SAMLAssertion samlAssertion = new SAMLAssertion();
            samlAssertion.setAssertion(assertion);

            return samlAssertion;

        } catch (SignatureException e) {
            log.error("error", e);
            throw new AssertionException("An error ocurred while trying to sign the assertion: "
                    + e.getMessage(), e);
        } catch (MarshallingException e) {
            log.error("error", e);
            throw new AssertionException(
                    "An error ocurred while trying to marshall the assertion: " + e.getMessage(), e);
        } catch (CertificateEncodingException e) {
            log.error("error", e);
            throw new AssertionException(
                    "An internal encoding error ocurred while trying to create the assertion: "
                    + e.getMessage(), e);
        } catch (ConfigurationException e) {
            log.error("error", e);
            throw new AssertionException(
                    "A configuration error ocurred with the bootstrap configuration: "
                    + e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            log.error("error", e);
            throw new AssertionException(
                    "An error ocurred while trying to generate the UUID for the assertion: "
                    + e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    private Subject getSubjectForSignedToken(WSTrustRequestContext context, XMLObjectBuilderFactory builderFactory) {

        String subjName = context.getRst().getSecondaryParametersRol();//context.getPrincipalName();
        //System.out.println("=========================> ROL QUE LLEGA: "+subjName);
        // Create the NameIdentifier
        SAMLObjectBuilder nameIdBuilder = (SAMLObjectBuilder) builderFactory
                .getBuilder(NameIdentifier.DEFAULT_ELEMENT_NAME);
        NameIdentifier nameId = (NameIdentifier) nameIdBuilder.buildObject();
        nameId.setNameIdentifier(subjName);
        nameId.setFormat(NameIdentifier.EMAIL);
        //nameId.setDOM(nameIdBuilder.);
        // Create the SubjectConfirmation
        SAMLObjectBuilder confirmationMethodBuilder = (SAMLObjectBuilder) builderFactory
                .getBuilder(ConfirmationMethod.DEFAULT_ELEMENT_NAME);
        ConfirmationMethod confirmationMethod = (ConfirmationMethod) confirmationMethodBuilder
                .buildObject();
        confirmationMethod.setConfirmationMethod(AgesicConstants.SAML10_BEARER_CONFIRMATION_METHOD);

        SAMLObjectBuilder subjectConfirmationBuilder = (SAMLObjectBuilder) builderFactory
                .getBuilder(SubjectConfirmation.DEFAULT_ELEMENT_NAME);
        SubjectConfirmation subjectConfirmation = (SubjectConfirmation) subjectConfirmationBuilder
                .buildObject();
        subjectConfirmation.getConfirmationMethods().add(confirmationMethod);

        // Create the Subject
        SAMLObjectBuilder subjectBuilder = (SAMLObjectBuilder) builderFactory
                .getBuilder(Subject.DEFAULT_ELEMENT_NAME);
        Subject subject = (Subject) subjectBuilder.buildObject();

        subject.setNameIdentifier(nameId);
        subject.setSubjectConfirmation(subjectConfirmation);

        return subject;
    }

    // unificar con el siguiente?
    // por lo menos despejo un poco el issuetoken
    public ClientCredential getCredentialFromStoreBean(StoreBean keystoreBean) throws ProcessingException {

        ClientCredential credential = null;

        String password = keystoreBean.getStorePwd();
        String alias = keystoreBean.getAlias();
        String storeFilePath = keystoreBean.getStoreFilePath();

        try {

            credential = getCredential(password, storeFilePath, alias);

        } catch (KeyStoreException e1) {
            log.error("error", e1);
            throw new ProcessingException(e1);
        } catch (NoSuchAlgorithmException e1) {
            log.error("error", e1);
            throw new ProcessingException(e1);
        } catch (CertificateException e1) {
            log.error("error", e1);
            throw new ProcessingException(e1);
        } catch (UnrecoverableKeyException e1) {
            log.error("error", e1);
            throw new ProcessingException(e1);
        } catch (IOException e1) {
            log.error("error", e1);
            throw new ProcessingException(e1);
        }

        return credential;


    }

    // pregunta: el atributo x509certificate es static... pero en cada llamada lo cargo y lo piso...
    // idea: hacerlo static y que lo cargue una sola vez
    public ClientCredential getCredential(String keyStorePwd, String keyStoreFilePath, String alias)
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException,
            UnrecoverableKeyException {

        if (staticCredential != null && x509Certificate != null) {
            return staticCredential; //e
        }
        File keyStoreFile = new File(keyStoreFilePath);
        FileInputStream keyStoreFis;

        keyStoreFis = new FileInputStream(keyStoreFile);
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(keyStoreFis, keyStorePwd.toCharArray());
        x509Certificate = (java.security.cert.X509Certificate) keyStore.getCertificate(alias);
        java.security.Key key = keyStore.getKey(alias, keyStorePwd.toCharArray());
        BasicX509Credential credential = new BasicX509Credential();
        credential.setEntityCertificate(x509Certificate);
        // TODO Ver que pasa con los certificados revocados!
        Collection<java.security.cert.X509CRL> crls = new ArrayList<X509CRL>();
        credential.setCRLs(crls);
        credential.setPrivateKey((PrivateKey) key);
        credential.setPublicKey(x509Certificate.getPublicKey());
        credential.getKeyNames().add(alias);

        ClientCredential result = new ClientCredential();
        result.setCredential(credential);
        staticCredential = result; //e
        return result;

    }
    /*	
     private Boolean checkValidBase(WSTrustRequestContext context) {
		
     RequestSecurityToken rst = context.getRst();
     Element base = rst.getBase();
     // por ahora chequeo que haya base
     if (base!=null) return true;
		
     return false;
     }
     */

    private AttributeStatement buildAttributeStatement(WSTrustRequestContext context, XMLObjectBuilderFactory builderFactory) {

        if (context.getTokenAttributes().isEmpty()) {
            return null;
        }

        SAMLObjectBuilder attrStatementBuilder = (SAMLObjectBuilder) builderFactory.getBuilder(AttributeStatement.DEFAULT_ELEMENT_NAME);
        AttributeStatement attrStatement = (AttributeStatement) attrStatementBuilder.buildObject();

        Subject attrSubject = getSubjectForSignedToken(context, builderFactory);
        attrStatement.setSubject(attrSubject);


        for (TokenAttribute att : context.getTokenAttributes()) {

            //attribute
            SAMLObjectBuilder attrBuilder = (SAMLObjectBuilder) builderFactory.getBuilder(Attribute.DEFAULT_ELEMENT_NAME);
            Attribute attrGroups = (Attribute) attrBuilder.buildObject();
            attrGroups.setAttributeName(att.getName());
            if (att.getNameSpace() != null) {
                attrGroups.setAttributeNamespace(att.getNameSpace());
            }
            XMLObjectBuilder stringBuilder = builderFactory.getBuilder(XSString.TYPE_NAME);
            XSString attrNewValue = (XSString) stringBuilder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSString.TYPE_NAME);
            attrNewValue.setValue(att.getValue());

            attrGroups.getAttributeValues().add(attrNewValue);

            attrStatement.getAttributes().add(attrGroups);

        }


        return attrStatement;


    }
}
