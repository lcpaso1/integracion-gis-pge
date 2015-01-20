
package edu.pge_gis.ctp.rc.gis_ws_client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the edu.pge_gis.ctp.rc.gis_ws_client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetCapabilities_QNAME = new QName("http://sc.ctp.pge_gis.edu/", "getCapabilities");
    private final static QName _TransactionResponse_QNAME = new QName("http://sc.ctp.pge_gis.edu/", "transactionResponse");
    private final static QName _GetMapResponse_QNAME = new QName("http://sc.ctp.pge_gis.edu/", "getMapResponse");
    private final static QName _GetFeatureResponse_QNAME = new QName("http://sc.ctp.pge_gis.edu/", "getFeatureResponse");
    private final static QName _Transaction_QNAME = new QName("http://sc.ctp.pge_gis.edu/", "transaction");
    private final static QName _LockFeature_QNAME = new QName("http://sc.ctp.pge_gis.edu/", "lockFeature");
    private final static QName _GetFeatureInfo_QNAME = new QName("http://sc.ctp.pge_gis.edu/", "getFeatureInfo");
    private final static QName _GetFeatureInfoResponse_QNAME = new QName("http://sc.ctp.pge_gis.edu/", "getFeatureInfoResponse");
    private final static QName _GetGmlObject_QNAME = new QName("http://sc.ctp.pge_gis.edu/", "getGmlObject");
    private final static QName _GetGmlObjectResponse_QNAME = new QName("http://sc.ctp.pge_gis.edu/", "getGmlObjectResponse");
    private final static QName _DescribeFeatureType_QNAME = new QName("http://sc.ctp.pge_gis.edu/", "describeFeatureType");
    private final static QName _LockFeatureResponse_QNAME = new QName("http://sc.ctp.pge_gis.edu/", "lockFeatureResponse");
    private final static QName _GetCapabilitiesResponse_QNAME = new QName("http://sc.ctp.pge_gis.edu/", "getCapabilitiesResponse");
    private final static QName _GetFeature_QNAME = new QName("http://sc.ctp.pge_gis.edu/", "getFeature");
    private final static QName _DescribeFeatureTypeResponse_QNAME = new QName("http://sc.ctp.pge_gis.edu/", "describeFeatureTypeResponse");
    private final static QName _GetMap_QNAME = new QName("http://sc.ctp.pge_gis.edu/", "getMap");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: edu.pge_gis.ctp.rc.gis_ws_client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetCapabilitiesResponse }
     * 
     */
    public GetCapabilitiesResponse createGetCapabilitiesResponse() {
        return new GetCapabilitiesResponse();
    }

    /**
     * Create an instance of {@link GetMap }
     * 
     */
    public GetMap createGetMap() {
        return new GetMap();
    }

    /**
     * Create an instance of {@link DescribeFeatureType }
     * 
     */
    public DescribeFeatureType createDescribeFeatureType() {
        return new DescribeFeatureType();
    }

    /**
     * Create an instance of {@link GetMapResponse }
     * 
     */
    public GetMapResponse createGetMapResponse() {
        return new GetMapResponse();
    }

    /**
     * Create an instance of {@link LockFeature }
     * 
     */
    public LockFeature createLockFeature() {
        return new LockFeature();
    }

    /**
     * Create an instance of {@link GetGmlObjectResponse }
     * 
     */
    public GetGmlObjectResponse createGetGmlObjectResponse() {
        return new GetGmlObjectResponse();
    }

    /**
     * Create an instance of {@link DescribeFeatureTypeResponse }
     * 
     */
    public DescribeFeatureTypeResponse createDescribeFeatureTypeResponse() {
        return new DescribeFeatureTypeResponse();
    }

    /**
     * Create an instance of {@link GetCapabilities }
     * 
     */
    public GetCapabilities createGetCapabilities() {
        return new GetCapabilities();
    }

    /**
     * Create an instance of {@link Transaction }
     * 
     */
    public Transaction createTransaction() {
        return new Transaction();
    }

    /**
     * Create an instance of {@link GetFeatureInfo }
     * 
     */
    public GetFeatureInfo createGetFeatureInfo() {
        return new GetFeatureInfo();
    }

    /**
     * Create an instance of {@link GisParams }
     * 
     */
    public GisParams createGisParams() {
        return new GisParams();
    }

    /**
     * Create an instance of {@link GetFeatureResponse }
     * 
     */
    public GetFeatureResponse createGetFeatureResponse() {
        return new GetFeatureResponse();
    }

    /**
     * Create an instance of {@link GetGmlObject }
     * 
     */
    public GetGmlObject createGetGmlObject() {
        return new GetGmlObject();
    }

    /**
     * Create an instance of {@link LockFeatureResponse }
     * 
     */
    public LockFeatureResponse createLockFeatureResponse() {
        return new LockFeatureResponse();
    }

    /**
     * Create an instance of {@link GetFeatureInfoResponse }
     * 
     */
    public GetFeatureInfoResponse createGetFeatureInfoResponse() {
        return new GetFeatureInfoResponse();
    }

    /**
     * Create an instance of {@link GetFeature }
     * 
     */
    public GetFeature createGetFeature() {
        return new GetFeature();
    }

    /**
     * Create an instance of {@link TransactionResponse }
     * 
     */
    public TransactionResponse createTransactionResponse() {
        return new TransactionResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCapabilities }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sc.ctp.pge_gis.edu/", name = "getCapabilities")
    public JAXBElement<GetCapabilities> createGetCapabilities(GetCapabilities value) {
        return new JAXBElement<GetCapabilities>(_GetCapabilities_QNAME, GetCapabilities.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransactionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sc.ctp.pge_gis.edu/", name = "transactionResponse")
    public JAXBElement<TransactionResponse> createTransactionResponse(TransactionResponse value) {
        return new JAXBElement<TransactionResponse>(_TransactionResponse_QNAME, TransactionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMapResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sc.ctp.pge_gis.edu/", name = "getMapResponse")
    public JAXBElement<GetMapResponse> createGetMapResponse(GetMapResponse value) {
        return new JAXBElement<GetMapResponse>(_GetMapResponse_QNAME, GetMapResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFeatureResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sc.ctp.pge_gis.edu/", name = "getFeatureResponse")
    public JAXBElement<GetFeatureResponse> createGetFeatureResponse(GetFeatureResponse value) {
        return new JAXBElement<GetFeatureResponse>(_GetFeatureResponse_QNAME, GetFeatureResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Transaction }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sc.ctp.pge_gis.edu/", name = "transaction")
    public JAXBElement<Transaction> createTransaction(Transaction value) {
        return new JAXBElement<Transaction>(_Transaction_QNAME, Transaction.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LockFeature }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sc.ctp.pge_gis.edu/", name = "lockFeature")
    public JAXBElement<LockFeature> createLockFeature(LockFeature value) {
        return new JAXBElement<LockFeature>(_LockFeature_QNAME, LockFeature.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFeatureInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sc.ctp.pge_gis.edu/", name = "getFeatureInfo")
    public JAXBElement<GetFeatureInfo> createGetFeatureInfo(GetFeatureInfo value) {
        return new JAXBElement<GetFeatureInfo>(_GetFeatureInfo_QNAME, GetFeatureInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFeatureInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sc.ctp.pge_gis.edu/", name = "getFeatureInfoResponse")
    public JAXBElement<GetFeatureInfoResponse> createGetFeatureInfoResponse(GetFeatureInfoResponse value) {
        return new JAXBElement<GetFeatureInfoResponse>(_GetFeatureInfoResponse_QNAME, GetFeatureInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGmlObject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sc.ctp.pge_gis.edu/", name = "getGmlObject")
    public JAXBElement<GetGmlObject> createGetGmlObject(GetGmlObject value) {
        return new JAXBElement<GetGmlObject>(_GetGmlObject_QNAME, GetGmlObject.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGmlObjectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sc.ctp.pge_gis.edu/", name = "getGmlObjectResponse")
    public JAXBElement<GetGmlObjectResponse> createGetGmlObjectResponse(GetGmlObjectResponse value) {
        return new JAXBElement<GetGmlObjectResponse>(_GetGmlObjectResponse_QNAME, GetGmlObjectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DescribeFeatureType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sc.ctp.pge_gis.edu/", name = "describeFeatureType")
    public JAXBElement<DescribeFeatureType> createDescribeFeatureType(DescribeFeatureType value) {
        return new JAXBElement<DescribeFeatureType>(_DescribeFeatureType_QNAME, DescribeFeatureType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LockFeatureResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sc.ctp.pge_gis.edu/", name = "lockFeatureResponse")
    public JAXBElement<LockFeatureResponse> createLockFeatureResponse(LockFeatureResponse value) {
        return new JAXBElement<LockFeatureResponse>(_LockFeatureResponse_QNAME, LockFeatureResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCapabilitiesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sc.ctp.pge_gis.edu/", name = "getCapabilitiesResponse")
    public JAXBElement<GetCapabilitiesResponse> createGetCapabilitiesResponse(GetCapabilitiesResponse value) {
        return new JAXBElement<GetCapabilitiesResponse>(_GetCapabilitiesResponse_QNAME, GetCapabilitiesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFeature }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sc.ctp.pge_gis.edu/", name = "getFeature")
    public JAXBElement<GetFeature> createGetFeature(GetFeature value) {
        return new JAXBElement<GetFeature>(_GetFeature_QNAME, GetFeature.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DescribeFeatureTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sc.ctp.pge_gis.edu/", name = "describeFeatureTypeResponse")
    public JAXBElement<DescribeFeatureTypeResponse> createDescribeFeatureTypeResponse(DescribeFeatureTypeResponse value) {
        return new JAXBElement<DescribeFeatureTypeResponse>(_DescribeFeatureTypeResponse_QNAME, DescribeFeatureTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMap }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sc.ctp.pge_gis.edu/", name = "getMap")
    public JAXBElement<GetMap> createGetMap(GetMap value) {
        return new JAXBElement<GetMap>(_GetMap_QNAME, GetMap.class, null, value);
    }

}
