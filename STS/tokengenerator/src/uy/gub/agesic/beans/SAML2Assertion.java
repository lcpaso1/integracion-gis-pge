package uy.gub.agesic.beans;

import org.opensaml.saml2.core.Assertion;
import org.opensaml.xml.util.XMLHelper;
import org.w3c.dom.Element;

/**
 * @author Guzman Llambias
 *
 */
public class SAML2Assertion {

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

    public Element getDOM() {
        return this.assertion.getDOM();
    }

    @Override
    public String toString() {
        return XMLHelper.prettyPrintXML(this.assertion.getDOM());
    }
}
