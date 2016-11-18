package gameeditor.xmlcreator;

import org.w3c.dom.Document;

//TODO: Implement the first four interfaces in gameeditor
public class GameEditorXMLLandingSpot {
//	private Document myXML;
	private GameEditorXMLManager myManager;
	
	public GameEditorXMLLandingSpot(){
		myManager = new GameEditorXMLManager();
	}
	
	
	public Document getXML(){
		return myManager.getXML();
	}
}
