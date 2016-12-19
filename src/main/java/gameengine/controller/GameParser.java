package gameengine.controller;

import gameengine.controller.interfaces.Parser;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import objects.Game;

/**
 * @author ericsong
 */
public class GameParser implements Parser{

    private XStream mySerializer = new XStream(new DomDriver());

	public Game convertXMLtoGame(String xmlData){
        return (Game) mySerializer.fromXML(xmlData);
	}




}
