package gameengine.controller.interfaces;

import objects.Game;

/**
 * The purpose of this class is to parse the XML file being passed from integration and instantiate classes
 * both from the Game Editor and the Game Engine based on the data in the XML.
 */
public interface Parser{

	public Game convertXMLtoGame(String xmlData);
}
