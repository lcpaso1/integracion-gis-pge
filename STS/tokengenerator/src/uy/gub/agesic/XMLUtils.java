/**
 *
 */
package uy.gub.agesic;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Guzman Llambias
 *
 */
public class XMLUtils {

    /**
     * Creates a string representation of a {@link Node} instance. This method
     * does not introduce any character to the string representation of the
     * {@link Node} (eg. \n or \r characters)
     *
     * @param node A {@link Node} instance
     * @return A string representation of the node instance
     * @throws RequestSecurityTokenException
     */
    public static String xmlToString(Node node) throws Exception {
        try {
            Source source = new DOMSource(node);
            StringWriter stringWriter = new StringWriter();
            Result result = new StreamResult(stringWriter);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
                    "yes");
            transformer.transform(source, result);
            return stringWriter.getBuffer().toString();

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            throw new Exception("Cannot build a string representation of the assertion.");
        } catch (TransformerException e) {
            e.printStackTrace();
            throw new Exception("Cannot build a string representation of the assertion.");
        }
    }

    /**
     * @param string Creates a {@link Document} from a string.
     * @return A {@link Document} representation of the string.
     * @throws ParserConfigurationException if a DocumentBuilder cannot be
     * created which satisfies the configuration requested
     * @throws SAXException if any parse errors occur
     * @throws IOException if any IO errors occur.
     *
     */
    public static Document stringToXml(String string) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(string));
        if (builder.isNamespaceAware()) {
            System.out.println("#######################3Is aware");
        } else {
            System.out.println("#######################Not aware");
        }
        return builder.parse(is);
    }
}
