
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

    private final static QName _Getmap_QNAME = new QName("http://sc.ctp.pge_gis.edu/", "getmap");
    private final static QName _GetfeatureinfoResponse_QNAME = new QName("http://sc.ctp.pge_gis.edu/", "getfeatureinfoResponse");
    private final static QName _TransactionResponse_QNAME = new QName("http://sc.ctp.pge_gis.edu/", "transactionResponse");
    private final static QName _Getfeatureinfo_QNAME = new QName("http://sc.ctp.pge_gis.edu/", "getfeatureinfo");
    private final static QName _Describefeaturetype_QNAME = new QName("http://sc.ctp.pge_gis.edu/", "describefeaturetype");
    private final static QName _Getcapabilities_QNAME = new QName("http://sc.ctp.pge_gis.edu/", "getcapabilities");
    private final static QName _Lockfeature_QNAME = new QName("http://sc.ctp.pge_gis.edu/", "lockfeature");
    private final static QName _Getfeature_QNAME = new QName("http://sc.ctp.pge_gis.edu/", "getfeature");
    private final static QName _Transaction_QNAME = new QName("http://sc.ctp.pge_gis.edu/", "transaction");
    private final static QName _LockfeatureResponse_QNAME = new QName("http://sc.ctp.pge_gis.edu/", "lockfeatureResponse");
    private final static QName _GetcapabilitiesResponse_QNAME = new QName("http://sc.ctp.pge_gis.edu/", "getcapabilitiesResponse");
    private final static QName _GetfeatureResponse_QNAME = new QName("http://sc.ctp.pge_gis.edu/", "getfeatureResponse");
    private final static QName _GetgmlobjectResponse_QNAME = new QName("http://sc.ctp.pge_gis.edu/", "getgmlobjectResponse");
    private final static QName _DescribefeaturetypeResponse_QNAME = new QName("http://sc.ctp.pge_gis.edu/", "describefeaturetypeResponse");
    private final static QName _GetmapResponse_QNAME = new QName("http://sc.ctp.pge_gis.edu/", "getmapResponse");
    private final static QName _Getgmlobject_QNAME = new QName("http://sc.ctp.pge_gis.edu/", "getgmlobject");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: edu.pge_gis.ctp.rc.gis_ws_client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetfeatureResponse }
     * 
     */
    public GetfeatureResponse createGetfeatureResponse() {
        return new GetfeatureResponse();
    }

    /**
     * Create an instance of {@link GetgmlobjectResponse }
     * 
     */
    public GetgmlobjectResponse createGetgmlobjectResponse() {
        return new GetgmlobjectResponse();
    }

    /**
     * Create an instance of {@link DescribefeaturetypeResponse }
     * 
     */
    public DescribefeaturetypeResponse createDescribefeaturetypeResponse() {
        return new DescribefeaturetypeResponse();
    }

    /**
     * Create an instance of {@link GetcapabilitiesResponse }
     * 
     */
    public GetcapabilitiesResponse createGetcapabilitiesResponse() {
        return new GetcapabilitiesResponse();
    }

    /**
     * Create an instance of {@link Describefeaturetype }
     * 
     */
    public Describefeaturetype createDescribefeaturetype() {
        return new Describefeaturetype();
    }

    /**
     * Create an instance of {@link Getmap }
     * 
     */
    public Getmap createGetmap() {
        return new Getmap();
    }

    /**
     * Create an instance of {@link GisParams }
     * 
     */
    public GisParams createGisParams() {
        return new GisParams();
    }

    /**
     * Create an instance of {@link Getfeature }
     * 
     */
    public Getfeature createGetfeature() {
        return new Getfeature();
    }

    /**
     * Create an instance of {@link GetmapResponse }
     * 
     */
    public GetmapResponse createGetmapResponse() {
        return new GetmapResponse();
    }

    /**
     * Create an instance of {@link Getfeatureinfo }
     * 
     */
    public Getfeatureinfo createGetfeatureinfo() {
        return new Getfeatureinfo();
    }

    /**
     * Create an instance of {@link Getgmlobject }
     * 
     */
    public Getgmlobject createGetgmlobject() {
        return new Getgmlobject();
    }

    /**
     * Create an instance of {@link GetfeatureinfoResponse }
     * 
     */
    public GetfeatureinfoResponse createGetfeatureinfoResponse() {
        return new GetfeatureinfoResponse();
    }

    /**
     * Create an instance of {@link LockfeatureResponse }
     * 
     */
    public LockfeatureResponse createLockfeatureResponse() {
        return new LockfeatureResponse();
    }

    /**
     * Create an instance of {@link Transaction }
     * 
     */
    public Transaction createTransaction() {
        return new Transaction();
    }

    /**
     * Create an instance of {@link TransactionResponse }
     * 
     */
    public TransactionResponse createTransactionResponse() {
        return new TransactionResponse();
    }

    /**
     * Create an instance of {@link Getcapabilities }
     * 
     */
    public Getcapabilities createGetcapabilities() {
        return new Getcapabilities();
    }

    /**
     * Create an instance of {@link Lockfeature }
     * 
     */
    public Lockfeature createLockfeature() {
        return new Lockfeature();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Getmap }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sc.ctp.pge_gis.edu/", name = "getmap")
    public JAXBElement<Getmap> createGetmap(Getmap value) {
        return new JAXBElement<Getmap>(_Getmap_QNAME, Getmap.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetfeatureinfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sc.ctp.pge_gis.edu/", name = "getfeatureinfoResponse")
    public JAXBElement<GetfeatureinfoResponse> createGetfeatureinfoResponse(GetfeatureinfoResponse value) {
        return new JAXBElement<GetfeatureinfoResponse>(_GetfeatureinfoResponse_QNAME, GetfeatureinfoResponse.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link Getfeatureinfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sc.ctp.pge_gis.edu/", name = "getfeatureinfo")
    public JAXBElement<Getfeatureinfo> createGetfeatureinfo(Getfeatureinfo value) {
        return new JAXBElement<Getfeatureinfo>(_Getfeatureinfo_QNAME, Getfeatureinfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Describefeaturetype }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sc.ctp.pge_gis.edu/", name = "describefeaturetype")
    public JAXBElement<Describefeaturetype> createDescribefeaturetype(Describefeaturetype value) {
        return new JAXBElement<Describefeaturetype>(_Describefeaturetype_QNAME, Describefeaturetype.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Getcapabilities }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sc.ctp.pge_gis.edu/", name = "getcapabilities")
    public JAXBElement<Getcapabilities> createGetcapabilities(Getcapabilities value) {
        return new JAXBElement<Getcapabilities>(_Getcapabilities_QNAME, Getcapabilities.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Lockfeature }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sc.ctp.pge_gis.edu/", name = "lockfeature")
    public JAXBElement<Lockfeature> createLockfeature(Lockfeature value) {
        return new JAXBElement<Lockfeature>(_Lockfeature_QNAME, Lockfeature.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Getfeature }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sc.ctp.pge_gis.edu/", name = "getfeature")
    public JAXBElement<Getfeature> createGetfeature(Getfeature value) {
        return new JAXBElement<Getfeature>(_Getfeature_QNAME, Getfeature.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link LockfeatureResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sc.ctp.pge_gis.edu/", name = "lockfeatureResponse")
    public JAXBElement<LockfeatureResponse> createLockfeatureResponse(LockfeatureResponse value) {
        return new JAXBElement<LockfeatureResponse>(_LockfeatureResponse_QNAME, LockfeatureResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetcapabilitiesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sc.ctp.pge_gis.edu/", name = "getcapabilitiesResponse")
    public JAXBElement<GetcapabilitiesResponse> createGetcapabilitiesResponse(GetcapabilitiesResponse value) {
        return new JAXBElement<GetcapabilitiesResponse>(_GetcapabilitiesResponse_QNAME, GetcapabilitiesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetfeatureResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sc.ctp.pge_gis.edu/", name = "getfeatureResponse")
    public JAXBElement<GetfeatureResponse> createGetfeatureResponse(GetfeatureResponse value) {
        return new JAXBElement<GetfeatureResponse>(_GetfeatureResponse_QNAME, GetfeatureResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetgmlobjectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sc.ctp.pge_gis.edu/", name = "getgmlobjectResponse")
    public JAXBElement<GetgmlobjectResponse> createGetgmlobjectResponse(GetgmlobjectResponse value) {
        return new JAXBElement<GetgmlobjectResponse>(_GetgmlobjectResponse_QNAME, GetgmlobjectResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DescribefeaturetypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sc.ctp.pge_gis.edu/", name = "describefeaturetypeResponse")
    public JAXBElement<DescribefeaturetypeResponse> createDescribefeaturetypeResponse(DescribefeaturetypeResponse value) {
        return new JAXBElement<DescribefeaturetypeResponse>(_DescribefeaturetypeResponse_QNAME, DescribefeaturetypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetmapResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sc.ctp.pge_gis.edu/", name = "getmapResponse")
    public JAXBElement<GetmapResponse> createGetmapResponse(GetmapResponse value) {
        return new JAXBElement<GetmapResponse>(_GetmapResponse_QNAME, GetmapResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Getgmlobject }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sc.ctp.pge_gis.edu/", name = "getgmlobject")
    public JAXBElement<Getgmlobject> createGetgmlobject(Getgmlobject value) {
        return new JAXBElement<Getgmlobject>(_Getgmlobject_QNAME, Getgmlobject.class, null, value);
    }

}
