package usecases;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import base.gameeditor.IGameEditorCreateGObject;
import base.gameeditor.IGameEditorXML;
import usecases.mockObjects.UseCaseBehavior;
import usecases.mockObjects.UseCaseGObject;

/**
 * 
 * This is a use case that corresponds to the following situation:
 * 
 * The button for "create main button" is pressed in the Game Editor,
 * which creates a GObject element in the XML file that will be
 * passed on to the Game Engine.
 * 
 * @author Ray Song(ys101)
 *
 */
public class UseCaseCreateMainCharacter{
	
	Document myXML;
	UseCaseGObject mainCharacter;

	public void createXMLFile() {
		DocumentBuilderFactory dFact = DocumentBuilderFactory.newInstance();
        DocumentBuilder build = null;;
		try {
			build = dFact.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			System.out.println("Error in initializing document!");
		}
        myXML = build.newDocument();
        Element root = myXML.createElement("root");
        myXML.appendChild(root);
        Element memberList = myXML.createElement("members");
        root.appendChild(memberList);
	}

}
