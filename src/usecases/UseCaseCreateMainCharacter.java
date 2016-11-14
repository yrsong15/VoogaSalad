package usecases;
import com.sun.xml.internal.txw2.Document;
import base.gameeditor.IGameEditorCreateMainCharacter;
import base.gameeditor.IGameEditorXML;

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
public class UseCaseCreateMainCharacter implements IGameEditorCreateMainCharacter, IGameEditorXML{
	
	Document myXML;

	@Override
	public void createXMLFile() {
		// TODO Auto-generated method stub
	}

	@Override
	public Document getXML() {
		return myXML;
	}

	@Override
	public void createGObject() {
		// TODO Auto-generated method stub
	}

	@Override
	public void setBehavior() {
		// TODO Auto-generated method stub
		
	}

}
