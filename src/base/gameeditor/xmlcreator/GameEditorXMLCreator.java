package base.gameeditor.xmlcreator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 * This is the main class for the Game Editor back-end where the XML file
 * that will eventually be passed off to the Game Engine is created.
 * 
 * @author Ray Song(ys101)
 *
 */
//TODO: Refine addElem, addAttr methods
//TODO: Add "addText" methods and the like
public class GameEditorXMLCreator {
	private final String DEFAULT_RESOURCE_PACKAGE = "resources/properties/";
	private final String XML_PROPERTIES_TITLE = "GameEditorXML";
	ResourceBundle rb;
	
	Map<String, Element> elemMap;
	private Document myXML;
	private Element rootElem;
	
	public GameEditorXMLCreator(){
		rb = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE+XML_PROPERTIES_TITLE);
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		elemMap = new HashMap<String, Element>();
		try {
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			myXML = docBuilder.newDocument();
			rootElem = myXML.createElement(rb.getString("rootElemTitle"));
			myXML.appendChild(rootElem);
		} catch (ParserConfigurationException e) {
			System.out.println(rb.getString("XMLInstantiationErrorMsg"));
		}
	}
	
	public void addElement(String name){
		Element elem = myXML.createElement(name);
		rootElem.appendChild(elem);
		elemMap.put(name, elem);
	}
	
	public void addAttributeToElem(String elemName, String attrName, String value){
		Element elem = elemMap.get(elemName);
		elem.setAttribute(attrName, value);
	}
	
	public void addTextToElem(String elemName, String text){
		Element elem = elemMap.get(elemName);
		elem.appendChild(myXML.createTextNode(text));
	}

	/**
	 * This method will output the current XML file to the console for test purposes
	 */
	public void testWriteXML(){
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		try {
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(myXML);
			StreamResult result = new StreamResult(System.out);
//			StreamResult result = new StreamResult(new File("C:\\file.xml")); //This code creates new XML file
			transformer.transform(source, result);
			System.out.println("File saved!");
		} catch (TransformerConfigurationException e) {
			System.out.println(rb.getString("TransformerConfigErrorMsg"));
		} catch (TransformerException e) {
			System.out.println(rb.getString("TransformerErrorMsg"));
		}
	}

	public Document getXML(){
		return myXML;
	}
}
