package edu.pge_gis.utils;

import java.io.OutputStream;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Element;

public class XMLUtils {

	
	public static void prettyPrint(Element element, OutputStream output){
		try {
			OutputFormat format = new OutputFormat(element.getOwnerDocument());
			format.setIndenting(true);
			XMLSerializer serializer = new XMLSerializer(output, format);
			serializer.serialize(element.getOwnerDocument());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
