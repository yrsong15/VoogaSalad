package base.gameeditor;

import org.w3c.dom.Document;

/**
 * 
 * @author @author Ray Song(ys101)
 *
 */
public interface IGameEditorXML {
	void addNewElement(String name);
	void addAttributeToElem(String elemName, String attrName, String value);
	void addTextToElem(String elemName, String text);
	void testWriteXML();
	public Document getXML();
}
