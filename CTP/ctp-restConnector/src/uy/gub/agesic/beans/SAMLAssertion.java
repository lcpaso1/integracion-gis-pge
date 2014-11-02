/**
 * 
 */
package uy.gub.agesic.beans;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.omg.SecurityAdmin.DomainAccessPolicyHolder;
import org.opensaml.saml1.core.Assertion;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * @author Guzman Llambias
 *
 */
public class SAMLAssertion {
	
	private Assertion assertion;

	/**
	 * @return the assertion
	 */
	public Assertion getAssertion() {
		return assertion;
	}

	/**
	 * @param assertion the assertion to set
	 */
	public void setAssertion(Assertion assertion) {
		this.assertion = assertion;
	}
	
	public Element getDOM(){
        try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(false);
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			Document d = db.parse("<dummy>hi</dummy>");
			
			return d.createElement("dummy");
		} catch (DOMException e) {
			e.printStackTrace();
			return null;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		} catch (SAXException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String toString(){
		return "";
	}
	

}
