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
import java.util.List;

import javax.xml.namespace.QName;

import org.joda.time.DateTime;
import org.opensaml.DefaultBootstrap;
import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.AttributeValue;
import org.opensaml.saml2.core.Audience;
import org.opensaml.saml2.core.AudienceRestriction;
import org.opensaml.saml2.core.Conditions;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.Subject;
import org.opensaml.saml2.core.SubjectConfirmation;
import org.opensaml.saml2.core.impl.AttributeBuilder;
import org.opensaml.saml2.core.impl.AttributeStatementBuilder;
import org.opensaml.saml2.core.impl.AudienceBuilder;
import org.opensaml.saml2.core.impl.AudienceRestrictionBuilder;
import org.opensaml.saml2.core.impl.ConditionsBuilder;
import org.opensaml.saml2.core.impl.IssuerBuilder;
import org.opensaml.saml2.core.impl.NameIDBuilder;
import org.opensaml.saml2.core.impl.SubjectBuilder;
import org.opensaml.saml2.core.impl.SubjectConfirmationBuilder;
import org.opensaml.samlext.saml2delrestrict.Delegate;
import org.opensaml.samlext.saml2delrestrict.DelegationRestrictionType;
import org.opensaml.samlext.saml2delrestrict.impl.DelegateBuilder;
import org.opensaml.samlext.saml2delrestrict.impl.DelegationRestrictionTypeBuilder;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallerFactory;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.schema.XSAny;
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
import uy.gub.agesic.beans.ClientCredential;
import uy.gub.agesic.beans.SAML2Assertion;
import uy.gub.agesic.beans.StoreBean;
import uy.gub.agesic.exceptions.AssertionException;

public class SAML2TokenProvider implements SecurityTokenProvider {

    // el sts usa esto para firmar
    private static X509Certificate x509Certificate;
    private static ClientCredential staticCredential;
    private static final Logger log = LoggerFactory.getLogger(SAML2TokenProvider.class);

    @Override
    public void IssueToken(WSTrustRequestContext context) throws RequestFailedException, InvalidRequestException {

        log.debug("IssueToken ini");

//referencia:
//http://social.msdn.microsoft.com/Forums/sk/Geneva/thread/79aca539-f6fe-4c41-9d27-e73cf949fa27
        context.setTokenType("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0");


        // Get Client Credential // TODO sacarlo para otro lado SAMLTokenProviderHelper
        StoreBean keystoreBean = context.getSigningKeystoreInformation();

        SAML2Assertion ass = null;

        try {
            ClientCredential credential = getCredentialFromStoreBean(keystoreBean);

            ass = this.generateSignedAssertion(credential, context);

        } catch (AssertionException e) {
            log.error("MYSTS error al generar la assertion", e);
            throw new RequestFailedException(e);
        } catch (ProcessingException e) {
            log.error("MYSTS error al generar la assertion - problema con keystore", e);
            throw new RequestFailedException(e);
        }

        context.setSecurityToken(ass.getDOM());
    }

    @SuppressWarnings("unchecked")
    public SAML2Assertion generateSignedAssertion(ClientCredential signingCredential, WSTrustRequestContext context) throws AssertionException {

        log.info(" >>>>>>>>>>>>>>>>>>>>>>>>> generateSignedAssertion");

        Credential credential = signingCredential.getCredential();

        DateTime issueInstant = new DateTime();
        DateTime conditionTimeNotBefore = issueInstant.minusSeconds(context.getTimeOut());
        DateTime conditionTimeNotAfter = issueInstant.plusSeconds(context.getTimeOut());


        try {


            DefaultBootstrap.bootstrap();

            // Get the builder factory
            XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();

            // Get the assertion builder based on the assertion element name
            SAMLObjectBuilder<Assertion> builder = (SAMLObjectBuilder<Assertion>) builderFactory.getBuilder(Assertion.DEFAULT_ELEMENT_NAME);


            Assertion assertion = builder.buildObject();
            assertion.setIssueInstant(issueInstant);
            log.info("se obtuvo la assertion");


            // Issuer ----------------------------------------------------------
            IssuerBuilder issuerBuild = (IssuerBuilder) builderFactory.getBuilder(Issuer.DEFAULT_ELEMENT_NAME);
            Issuer iss = issuerBuild.buildObject();
            iss.setValue(context.getIssuer());

            assertion.setIssuer(iss);



            // Subject ---------------------------------------------------------
            SubjectBuilder subjectBuild = (SubjectBuilder) builderFactory.getBuilder(Subject.DEFAULT_ELEMENT_NAME);
            Subject subj = subjectBuild.buildObject();
            //NameID --------
            NameIDBuilder nameBuild = (NameIDBuilder) builderFactory.getBuilder(NameID.DEFAULT_ELEMENT_NAME);
            NameID nameIdSubj = nameBuild.buildObject();
            nameIdSubj.setValue(context.getPrincipalName());
            subj.setNameID(nameIdSubj);
            // SubjectConfirmation -----
            final String delegateStr = context.getDelegate();
            if (delegateStr != null && !delegateStr.equals("")) {
                SubjectConfirmationBuilder subjconfBuild = (SubjectConfirmationBuilder) builderFactory.getBuilder(SubjectConfirmation.DEFAULT_ELEMENT_NAME);
                SubjectConfirmation sconf = subjconfBuild.buildObject();
                sconf.setMethod("urn:oasis:names:tc:SAML:2.0:cm:sender-vouches");
                NameID nameIdConfInterm = nameBuild.buildObject();
                nameIdConfInterm.setValue(delegateStr);
                nameIdConfInterm.setFormat("urn:oasis:names:tc:SAML:2.0:nameid-format:entity");
                sconf.setNameID(nameIdConfInterm);
                //SubjectConfirmation-->SubjectConfirmationData
//                SubjectConfirmationDataBuilder scdBuild = (SubjectConfirmationDataBuilder) builderFactory.getBuilder(SubjectConfirmationData.DEFAULT_ELEMENT_NAME);
//                SubjectConfirmationData scdInst = scdBuild.buildObject();
//                scdInst.setAddress("192.168.10.10");
//                sconf.setSubjectConfirmationData(scdInst);
                subj.getSubjectConfirmations().add(sconf);
            }

            assertion.setSubject(subj);


            // ------------------------------------------------------
            // Conditions ------------------------------------------------------
            ConditionsBuilder condsBuild = (ConditionsBuilder) builderFactory.getBuilder(Conditions.DEFAULT_ELEMENT_NAME);
            Conditions conditions = condsBuild.buildObject();
            conditions.setNotBefore(conditionTimeNotBefore);
            conditions.setNotOnOrAfter(conditionTimeNotAfter);
            //AudienceRestriction ---------------------------------------------
            AudienceRestrictionBuilder audRestBuilder = (AudienceRestrictionBuilder) builderFactory.getBuilder(AudienceRestriction.DEFAULT_ELEMENT_NAME);
            AudienceRestriction ar = audRestBuilder.buildObject();
            //Audience --------------------------------------------------------
            //TODO!!
            AudienceBuilder audBuild = (AudienceBuilder) builderFactory.getBuilder(Audience.DEFAULT_ELEMENT_NAME);

            String audienceStr = context.getRst().getAppliesToAddress();
            if (audienceStr.contains(";")) {
                String[] audienceArr = audienceStr.split(";");
                for (int i = 0; i < audienceArr.length; i++) {
                    String actualAudience = audienceArr[i];
                    Audience aud1 = audBuild.buildObject();
                    aud1.setAudienceURI(actualAudience);
                    ar.getAudiences().add(aud1);
                }
            } else {
                Audience aud1 = audBuild.buildObject();
                aud1.setAudienceURI(audienceStr);
                ar.getAudiences().add(aud1);
            }
            conditions.getAudienceRestrictions().add(ar);

            //Condition Delegation -------------------------------------------
            if (delegateStr != null && !delegateStr.equals("")) {
                DelegationRestrictionTypeBuilder delegRestrBuild = new DelegationRestrictionTypeBuilder();
                DelegationRestrictionType delegRestriction = delegRestrBuild.buildObject();
                DelegateBuilder delegBuild = (DelegateBuilder) builderFactory.getBuilder(Delegate.DEFAULT_ELEMENT_NAME);
                Delegate deleg1 = delegBuild.buildObject();
                deleg1.setConfirmationMethod("#sender-vouches"); // TODO ver esto
                NameID nameDeleg = nameBuild.buildObject();
                nameDeleg.setValue(delegateStr);
                nameDeleg.setFormat("urn:oasis:names:tc:SAML:2.0:nameid-format:entity");
                deleg1.setNameID(nameDeleg);
                delegRestriction.getDelegates().add(deleg1);
                conditions.getConditions().add(delegRestriction);
            }
            assertion.setConditions(conditions);

            AttributeStatement attrStatement = buildAttributeStatement(context, builderFactory);
            if (attrStatement != null) {
                assertion.getAttributeStatements().add(attrStatement);
            }


            //-----------------------------------------------------------------
            //Signature -------------------------------------------------------
            Signature signature = (Signature) builderFactory.getBuilder(Signature.DEFAULT_ELEMENT_NAME)
                    .buildObject(Signature.DEFAULT_ELEMENT_NAME);

            signature.setSigningCredential(credential);
            signature.setSignatureAlgorithm(SignatureConstants.ALGO_ID_SIGNATURE_RSA_SHA1);
            signature.setCanonicalizationAlgorithm(SignatureConstants.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);

            KeyInfoBuilder keyinfoBuilder = (KeyInfoBuilder) builderFactory.getBuilder(KeyInfo.DEFAULT_ELEMENT_NAME);
            KeyInfo keyInfo = (KeyInfo) keyinfoBuilder.buildObject(KeyInfo.DEFAULT_ELEMENT_NAME);
            KeyInfoHelper.addCertificate(keyInfo, ((BasicX509Credential) credential).getEntityCertificate());

            signature.setKeyInfo(keyInfo);

            assertion.setSignature(signature);

            Element assertXml = assertionToElement(assertion);

            Signer.signObject(signature);




            SAML2Assertion samlAssertion = new SAML2Assertion();
            samlAssertion.setAssertion(assertion);

            return samlAssertion;

//            return samlAssertion;

        } catch (SignatureException e) {
            log.error("An error ocurred while trying to sign the assertion: ", e);
            throw new AssertionException("An error ocurred while trying to sign the assertion: "
                    + e.getMessage(), e);
        } catch (CertificateEncodingException e) {
            log.error("An internal encoding error ocurred while trying to create the assertion: ", e);
            throw new AssertionException(
                    "An internal encoding error ocurred while trying to create the assertion: "
                    + e.getMessage(), e);
        } catch (ConfigurationException e) {
            log.error("A configuration error ocurred with the bootstrap configuration", e);
            throw new AssertionException(
                    "A configuration error ocurred with the bootstrap configuration: "
                    + e.getMessage(), e);
        } catch (MarshallingException ex) {
            log.error("MarshallingException ", ex);
            throw new AssertionException(
                    "A configuration error ocurred with the marshalling: "
                    + ex.getMessage(), ex);
        }
    }

    private static Element assertionToElement(Assertion assertion) throws MarshallingException {
        MarshallerFactory mf = Configuration.getMarshallerFactory();
        Marshaller marshaller = mf.getMarshaller(assertion);
        Element assertXml = marshaller.marshall(assertion);
        return assertXml;
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
            log.error("", e1);
            throw new ProcessingException(e1);
        } catch (NoSuchAlgorithmException e1) {
            log.error("", e1);
            throw new ProcessingException(e1);
        } catch (CertificateException e1) {
            log.error("", e1);
            throw new ProcessingException(e1);
        } catch (UnrecoverableKeyException e1) {
            log.error("", e1);
            throw new ProcessingException(e1);
        } catch (IOException e1) {
            log.error("", e1);
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
  
    private AttributeStatement buildAttributeStatement(WSTrustRequestContext context, XMLObjectBuilderFactory builderFactory) {

        if (context.getTokenAttributes().isEmpty()) {
            return null;
        }

        //AttributeStatement------------------------------------------------
        AttributeStatementBuilder attrStatBuild = (AttributeStatementBuilder) builderFactory.getBuilder(AttributeStatement.DEFAULT_ELEMENT_NAME);
        AttributeStatement attrStatement = attrStatBuild.buildObject();
        AttributeBuilder attrBuild = (AttributeBuilder) builderFactory.getBuilder(Attribute.DEFAULT_ELEMENT_NAME);
        XMLObjectBuilder<XSAny> xsAnyBuilder = builderFactory.getBuilder(XSAny.TYPE_NAME);

        List<TokenAttribute> tokenAttributes = context.getTokenAttributes();

        for (TokenAttribute tAttr : tokenAttributes) {
            log.info("actual att {}", tAttr);
            Attribute newAttr = attrBuild.buildObject();
            newAttr.setName(tAttr.getName());

            XSAny attrValue = xsAnyBuilder.buildObject(SAMLConstants.SAML20_NS, AttributeValue.DEFAULT_ELEMENT_LOCAL_NAME, SAMLConstants.SAML20_PREFIX);
            attrValue.setTextContent(tAttr.getValue());
            attrValue.getUnknownAttributes().put(new QName("http://www.w3.org/2001/XMLSchema-instance", "type", "xsi"), "xs:string");
            newAttr.getAttributeValues().add(attrValue);
            attrStatement.getAttributes().add(newAttr);

        }
        return attrStatement;
    }
}
