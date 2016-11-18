package base.gameengine.controller;

import base.gameengine.controller.interfaces.Parser;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @author ericsong
 *
 */
public class GameParser implements Parser {

    private XStream mySerializer = new XStream(new DomDriver());

	public GameParser() {

	}
	
	@Override
	public void processXML(String fileName){
        mySerializer.fromXML(fileName);
	}

}
