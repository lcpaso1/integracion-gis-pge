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
}
