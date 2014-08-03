package edu.pge_gis.ctp.sc;

import java.io.Serializable;

/**
 * este vo va a tener todos los parametros necesarios para wms y wfs
 * */
public class GISParams implements Serializable {
	
	/** getCapabilities
	 * 	obligatorios: service, request 
	 * 	opcionales: version, format, updateSequence
	 */
	
	/** getMap
	 * 	obligatorios: version, request, layers, styles, crs, bbox, width, heigth, format 
	 * 	opcionales: transparent, bgcolor, exceptions, time, elevation, otherSampleDimensions
	 */
	
	/** getFeatureInfo
	 * 	obligatorios: version, request, mapRequestPart, queryLayers, infoFormat, i, j  
	 * 	opcionales: featureCount, exceptions
	 */
	
	private String version; //ej: 1.3.0
	private String service; //ejs: WMS WFS
	private String request; //ejs: getCapabilities getMap getFeatureInfo
	private String format; //MIME_type: text/xml
	private String updateSequence; //??????
	private String layers;  //comma-separated list of one or more valid layer names
	private String styles; //comma-separated list of one or more valid style names
	private String crs; //ej: EPSG:31370
	private String bbox; //ej: minx,miny,maxx,maxy bounding box corners (lower left, upper rigth) in CRS units
	private int width; //width in pixels of map picture
	private int heigth; //heigth in pixels of map picture
	private String transparent; //los string "TRUE" o "FALSE"
	private String bgcolor; //0xRRGGBB
	private String exceptions; //ej: application/vnd.ogc.se_inimage: si la aplicación falla, se devuelve una imagen por defecto
	private String time; //??????
	private String elevation; //??????
	private String otherSampleDimensions; //??????
	private String mapRequestPart; //??????
	private String queryLayers; //comma-separated list of one or more layers to be queried
	private String infoFormat; //??????
	private int featureCount; //??????
	private int i; //i coordinate in pixels of feature in Map CS
	private int j; //j coordinate in pixels of feature in Map CS
	
	// faltan parametros de los metodos de WFS
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getUpdateSequence() {
		return updateSequence;
	}
	public void setUpdateSequence(String updateSequence) {
		this.updateSequence = updateSequence;
	}
	public String getLayers() {
		return layers;
	}
	public void setLayers(String layers) {
		this.layers = layers;
	}
	public String getStyles() {
		return styles;
	}
	public void setStyles(String styles) {
		this.styles = styles;
	}
	public String getCrs() {
		return crs;
	}
	public void setCrs(String crs) {
		this.crs = crs;
	}
	public String getBbox() {
		return bbox;
	}
	public void setBbox(String bbox) {
		this.bbox = bbox;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeigth() {
		return heigth;
	}
	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}
	public String getTransparent() {
		return transparent;
	}
	public void setTransparent(String transparent) {
		this.transparent = transparent;
	}
	public String getBgcolor() {
		return bgcolor;
	}
	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}
	public String getExceptions() {
		return exceptions;
	}
	public void setExceptions(String exceptions) {
		this.exceptions = exceptions;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getElevation() {
		return elevation;
	}
	public void setElevation(String elevation) {
		this.elevation = elevation;
	}
	public String getOtherSampleDimensions() {
		return otherSampleDimensions;
	}
	public void setOtherSampleDimensions(String otherSampleDimensions) {
		this.otherSampleDimensions = otherSampleDimensions;
	}
	public String getMapRequestPart() {
		return mapRequestPart;
	}
	public void setMapRequestPart(String mapRequestPart) {
		this.mapRequestPart = mapRequestPart;
	}
	public String getQueryLayers() {
		return queryLayers;
	}
	public void setQueryLayers(String queryLayers) {
		this.queryLayers = queryLayers;
	}
	public String getInfoFormat() {
		return infoFormat;
	}
	public void setInfoFormat(String infoFormat) {
		this.infoFormat = infoFormat;
	}
	public int getFeatureCount() {
		return featureCount;
	}
	public void setFeatureCount(int featureCount) {
		this.featureCount = featureCount;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public int getJ() {
		return j;
	}
	public void setJ(int j) {
		this.j = j;
	}
	

	
	
	
}
