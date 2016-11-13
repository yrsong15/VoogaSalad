package usecases;
import com.sun.xml.internal.txw2.Document;
import base.gameeditor.IGameEditorCreateMainCharacter;
import base.gameeditor.IGameEditorXML;


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
