package gameeditor.xml;

import java.util.ResourceBundle;

import org.w3c.dom.Document;

import gameeditor.interfaces.IGameEditorCreateGObject;


//TODO: Implement the first four interfaces in base.gameeditor
/**
 * This class interacts with the frontend Game Editor and exposes the methods that are responsible
 * for adding elements and attributes to the XML file.
 * @author Ray Song(ys101)
 *
 */
public class GameEditorXMLLandingSpot implements IGameEditorCreateGObject{
//TODO: Implement the first four interfaces in gameeditor
//	private Document myXML;
	private final String DEFAULT_RESOURCE_PACKAGE = "resources/properties/";
	private final String XML_PROPERTIES_TITLE = "GameEditorXML";
	private ResourceBundle rb;
	
	private GameEditorXMLManager myManager;
	
	public GameEditorXMLLandingSpot(){
		rb = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE+XML_PROPERTIES_TITLE);
		myManager = new GameEditorXMLManager();
		myManager.setResourceBundle(rb);
	}
	
	public Document getXML(){
		return myManager.getXML();
	}

	@Override
	public void createGObject(String type) {
		myManager.addNewElement(rb.getString("GObject"));
		myManager.addAttributeToElem(rb.getString("GObject"), rb.getString("type"), type);
	}


	@Override
	public void setPosition(String isRandom) {
		myManager.addElemToElem(rb.getString("GObject"), rb.getString("Property"));
//		myManager.addAttributeToElem(rb.getString("Property"), attrName, value);
	}

	@Override
	public void addXCorToProperty(String xcor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addYCorToProperty(String ycor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addSpacingToProperty(String spacing) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMinHeightToProperty(String minHeight) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMaxHeightToProperty(String maxHeight) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setImage(String filename) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPropertyHealth(String health) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPropertyDamage(String damage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPropertyPoints(String points) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPropertyStatic(String isStatic) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBehaviorInput(String input) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBehaviorCommand(String command) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBehaviorMethod(String behavior) {
		// TODO Auto-generated method stub
		
	}
}
