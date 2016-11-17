package base.gameeditor;

import org.w3c.dom.Document;

/**
 * 
 * @author @author Ray Song(ys101)
 *
 */
public interface IGameEditorXML {
	void addNewElement(String name);
	void addElemToElem(String parentName, String childName);
	void addAttributeToElem(String elemName, String attrName, String value);
	void addTextToElem(String elemName, String text);
	void writeXML(String filepath);
	public Document getXML();
}
