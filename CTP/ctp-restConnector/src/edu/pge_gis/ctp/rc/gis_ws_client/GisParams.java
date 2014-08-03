
package edu.pge_gis.ctp.rc.gis_ws_client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for gisParams complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="gisParams">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bbox" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bgcolor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="crs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="elevation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="exceptions" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="featureCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="format" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="heigth" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="i" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="infoFormat" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="j" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="layers" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mapRequestPart" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="otherSampleDimensions" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="queryLayers" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="request" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="service" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="styles" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="time" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transparent" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="updateSequence" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="width" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "gisParams", propOrder = {
    "bbox",
    "bgcolor",
    "crs",
    "elevation",
    "exceptions",
    "featureCount",
    "format",
    "heigth",
    "i",
    "infoFormat",
    "j",
    "layers",
    "mapRequestPart",
    "otherSampleDimensions",
    "queryLayers",
    "request",
    "service",
    "styles",
    "time",
    "transparent",
    "updateSequence",
    "version",
    "width"
})
public class GisParams {

    protected String bbox;
    protected String bgcolor;
    protected String crs;
    protected String elevation;
    protected String exceptions;
    protected int featureCount;
    protected String format;
    protected int heigth;
    protected int i;
    protected String infoFormat;
    protected int j;
    protected String layers;
    protected String mapRequestPart;
    protected String otherSampleDimensions;
    protected String queryLayers;
    protected String request;
    protected String service;
    protected String styles;
    protected String time;
    protected String transparent;
    protected String updateSequence;
    protected String version;
    protected int width;

    /**
     * Gets the value of the bbox property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBbox() {
        return bbox;
    }

    /**
     * Sets the value of the bbox property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBbox(String value) {
        this.bbox = value;
    }

    /**
     * Gets the value of the bgcolor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBgcolor() {
        return bgcolor;
    }

    /**
     * Sets the value of the bgcolor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBgcolor(String value) {
        this.bgcolor = value;
    }

    /**
     * Gets the value of the crs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCrs() {
        return crs;
    }

    /**
     * Sets the value of the crs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCrs(String value) {
        this.crs = value;
    }

    /**
     * Gets the value of the elevation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getElevation() {
        return elevation;
    }

    /**
     * Sets the value of the elevation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setElevation(String value) {
        this.elevation = value;
    }

    /**
     * Gets the value of the exceptions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExceptions() {
        return exceptions;
    }

    /**
     * Sets the value of the exceptions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExceptions(String value) {
        this.exceptions = value;
    }

    /**
     * Gets the value of the featureCount property.
     * 
     */
    public int getFeatureCount() {
        return featureCount;
    }

    /**
     * Sets the value of the featureCount property.
     * 
     */
    public void setFeatureCount(int value) {
        this.featureCount = value;
    }

    /**
     * Gets the value of the format property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormat() {
        return format;
    }

    /**
     * Sets the value of the format property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormat(String value) {
        this.format = value;
    }

    /**
     * Gets the value of the heigth property.
     * 
     */
    public int getHeigth() {
        return heigth;
    }

    /**
     * Sets the value of the heigth property.
     * 
     */
    public void setHeigth(int value) {
        this.heigth = value;
    }

    /**
     * Gets the value of the i property.
     * 
     */
    public int getI() {
        return i;
    }

    /**
     * Sets the value of the i property.
     * 
     */
    public void setI(int value) {
        this.i = value;
    }

    /**
     * Gets the value of the infoFormat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInfoFormat() {
        return infoFormat;
    }

    /**
     * Sets the value of the infoFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInfoFormat(String value) {
        this.infoFormat = value;
    }

    /**
     * Gets the value of the j property.
     * 
     */
    public int getJ() {
        return j;
    }

    /**
     * Sets the value of the j property.
     * 
     */
    public void setJ(int value) {
        this.j = value;
    }

    /**
     * Gets the value of the layers property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLayers() {
        return layers;
    }

    /**
     * Sets the value of the layers property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLayers(String value) {
        this.layers = value;
    }

    /**
     * Gets the value of the mapRequestPart property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMapRequestPart() {
        return mapRequestPart;
    }

    /**
     * Sets the value of the mapRequestPart property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMapRequestPart(String value) {
        this.mapRequestPart = value;
    }

    /**
     * Gets the value of the otherSampleDimensions property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtherSampleDimensions() {
        return otherSampleDimensions;
    }

    /**
     * Sets the value of the otherSampleDimensions property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtherSampleDimensions(String value) {
        this.otherSampleDimensions = value;
    }

    /**
     * Gets the value of the queryLayers property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQueryLayers() {
        return queryLayers;
    }

    /**
     * Sets the value of the queryLayers property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQueryLayers(String value) {
        this.queryLayers = value;
    }

    /**
     * Gets the value of the request property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequest() {
        return request;
    }

    /**
     * Sets the value of the request property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequest(String value) {
        this.request = value;
    }

    /**
     * Gets the value of the service property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getService() {
        return service;
    }

    /**
     * Sets the value of the service property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setService(String value) {
        this.service = value;
    }

    /**
     * Gets the value of the styles property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStyles() {
        return styles;
    }

    /**
     * Sets the value of the styles property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStyles(String value) {
        this.styles = value;
    }

    /**
     * Gets the value of the time property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTime(String value) {
        this.time = value;
    }

    /**
     * Gets the value of the transparent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransparent() {
        return transparent;
    }

    /**
     * Sets the value of the transparent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransparent(String value) {
        this.transparent = value;
    }

    /**
     * Gets the value of the updateSequence property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpdateSequence() {
        return updateSequence;
    }

    /**
     * Sets the value of the updateSequence property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdateSequence(String value) {
        this.updateSequence = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    /**
     * Gets the value of the width property.
     * 
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     * 
     */
    public void setWidth(int value) {
        this.width = value;
    }

}
