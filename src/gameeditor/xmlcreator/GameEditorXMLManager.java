package gameeditor.xmlcreator;

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

import gameeditor.IGameEditorXML;


/**
 * This is the main class for the Game Editor back-end where the XML file
 * that will eventually be passed off to the Game Engine is created.
 * 
 * @author Ray Song(ys101)
 *
 */
public class GameEditorXMLManager implements IGameEditorXML{
	private final String DEFAULT_RESOURCE_PACKAGE = "resources/properties/";
	private final String XML_PROPERTIES_TITLE = "GameEditorXML";
	ResourceBundle rb;
	
	Map<String, Element> elemMap;
	private Document myXML;
	private Element rootElem;
	private Element currElem;
	
	public GameEditorXMLManager(){
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
	
	public void addNewElement(String name){
		Element elem = myXML.createElement(name);
		currElem = elem;
		rootElem.appendChild(elem);
		elemMap.put(name, elem);
	}
	
	//TODO: refine method
	public void addElemToElem(String parentName, String childName){
		Element childElem = myXML.createElement(childName);
		if(currElem.getNodeName() == parentName){
			currElem.appendChild(childElem);
			currElem = childElem;
		}
		else{
			Element elem = elemMap.get(parentName);
			elem.appendChild(childElem);
			currElem = childElem;
		}
	}
	
	//TODO: "else" case in is not refined
	public void addAttributeToElem(String elemName, String attrName, String value){
		if(currElem.getNodeName() == elemName){
			currElem.setAttribute(attrName, value);
			elemMap.put(elemName, currElem);
		}
		else{
			Element elem = elemMap.get(elemName);
			elem.setAttribute(attrName, value);
		}
	}
	
	//TODO: "else" case in is not refined
	public void addTextToElem(String elemName, String text){
		if(currElem.getNodeName()==elemName){
			currElem.appendChild(myXML.createTextNode(text));
		}
		else{
			Element elem = elemMap.get(elemName);
			elem.appendChild(myXML.createTextNode(text));
		}
	}

	/**
	 * This test method will output the current XML file to the console for debugging purposes.
	 */
	void testWriteXML(){
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		try {
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(myXML);
			StreamResult result = new StreamResult(System.out);
//			StreamResult result = new StreamResult(new File("C:\\file.xml")); //This code creates new XML file
			transformer.transform(source, result);
			System.out.println();
			System.out.println("File saved!");
		} catch (TransformerConfigurationException e) {
			System.out.println(rb.getString("TransformerConfigErrorMsg"));
		} catch (TransformerException e) {
			System.out.println(rb.getString("TransformerErrorMsg"));
		}
	}
	
	public void writeXML(String filepath){
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		try {
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(myXML);
			StreamResult result = new StreamResult(new File(filepath)); //This code creates new XML file
			transformer.transform(source, result);
			System.out.println();
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
