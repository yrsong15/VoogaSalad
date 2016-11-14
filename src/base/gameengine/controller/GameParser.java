package base.gameengine.controller;

import org.w3c.dom.Element;

/**
 * @author ericsong
 *
 */
public class GameParser implements Parser{

	public GameParser() {
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public void processXML(String fileName) {
		XMLParser xmlParser = new XMLParser();
		Element element = xmlParser.getRootElement(fileName);
		//parse element here
	}

}
